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
import com.emerchantpay.task.validations.exceptions.ReferenceTransactionTypeInvalidException;
import com.emerchantpay.task.validations.exceptions.TransactionHasNoAmountException;
import com.emerchantpay.task.validations.exceptions.TransactionStatusInvalidException;
import com.emerchantpay.task.validations.exceptions.TransactionUuidAlreadyExistsException;
import com.emerchantpay.task.validations.interfaces.TransactionValidation;

@Service
public class TransactionValidationImpl implements TransactionValidation {

	@Autowired
	private TransactionRepository transactionRepository;

	@Value("${email.pattern.valid}")
	private String validEmailPattern;

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
		Optional<Transaction> reference = transactionRepository.findById(transactionDto.getReference().getId());
		if ((transactionDto.getType().equals(TransactionTypeDto.REFUND) && reference.isPresent()
				&& !reference.get().getType().equals(TransactionTypeDto.CHARGE.name()))
				|| (transactionDto.getType().equals(TransactionTypeDto.REVERSAL) && reference.isPresent()
						&& !reference.get().getType().equals(TransactionTypeDto.AUTHORIZE.name()))) {
			throw new ReferenceTransactionTypeInvalidException();

		}
	}

}
