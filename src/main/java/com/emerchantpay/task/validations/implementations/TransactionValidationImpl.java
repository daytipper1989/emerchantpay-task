package com.emerchantpay.task.validations.implementations;

import java.util.Optional;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.emerchantpay.task.dtos.TransactionDto;
import com.emerchantpay.task.dtos.enums.TransactionTypeDto;
import com.emerchantpay.task.models.Transaction;
import com.emerchantpay.task.repositories.TransactionRepository;
import com.emerchantpay.task.validations.exceptions.EmailInvalidException;
import com.emerchantpay.task.validations.exceptions.ReferenceTransactionStatusInvalidException;
import com.emerchantpay.task.validations.exceptions.ReferenceTransactionTypeInvalidException;
import com.emerchantpay.task.validations.exceptions.TransactionHasNoAmountException;
import com.emerchantpay.task.validations.exceptions.TransactionUuidAlreadyExistsException;
import com.emerchantpay.task.validations.interfaces.TransactionValidation;

@Service
public class TransactionValidationImpl implements TransactionValidation {

	@Autowired
	private TransactionRepository transactionRepository;

	@Value("${email.pattern.valid}")
	private String validEmailPattern;
	
	@Value("${transaction.status.approved}")
	private String approvedTransactionStatus;

	@Override
	public void validateUuid(String uuid) throws TransactionUuidAlreadyExistsException {
		Optional<Transaction> transactionOptional = transactionRepository.findByUuid(uuid);
		if (transactionOptional.isPresent()) {
			throw new TransactionUuidAlreadyExistsException();
		}
	}

	@Override
	public void validateEmail(String email) throws EmailInvalidException {
		if (!Pattern.compile(validEmailPattern).matcher(email).matches()) {
			throw new EmailInvalidException();
		}
	}

	@Override
	public void validateHasAmount(double amount) throws TransactionHasNoAmountException {
		if (amount <= 0.0d) {
			throw new TransactionHasNoAmountException();
		}
	}

	@Override
	public void validateReferenceTrasaction(TransactionDto transactionDto)
			throws ReferenceTransactionTypeInvalidException {
		Optional<Transaction> referenceOptional = transactionRepository.findById(transactionDto.getReference().getId());

		if (referenceOptional.isPresent()) {
			Transaction reference = referenceOptional.get();
			if ((transactionDto.getType().equals(TransactionTypeDto.REFUND)
					&& !reference.getType().equals(TransactionTypeDto.CHARGE.name()))
					|| (transactionDto.getType().equals(TransactionTypeDto.REVERSAL)
							&& !reference.getType().equals(TransactionTypeDto.AUTHORIZE.name()))) {
				throw new ReferenceTransactionTypeInvalidException();

			}

			if ((transactionDto.getType().equals(TransactionTypeDto.REFUND)
					&& !reference.getStatus().equals(approvedTransactionStatus))
					|| (transactionDto.getType().equals(TransactionTypeDto.REVERSAL)
							&& !reference.getStatus().equals(approvedTransactionStatus))) {
				throw new ReferenceTransactionStatusInvalidException();

			}
		}
	}

}
