import React, { useState, useEffect }  from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';  
import {Table } from 'react-bootstrap';

const Merchant = () => {
    const [error, setError] = useState(null);
    const [isLoaded, setIsLoaded] = useState(false);
    const [merchants, setMerchants] = useState([]);
    useEffect(() => {
        fetch("http://localhost:8080/merchants")
            .then(res => res.json())
            .then(
                (data) => {
                    setIsLoaded(true);
                    setMerchants(data);
                },
                (error) => {
                    setIsLoaded(true);
                    setError(error);
                }
            )
      }, [])
if (error) {
        return <div>Error: {error.message}</div>;
    } else if (!isLoaded) {
        return <div>Loading...</div>;
    } else {
        return (
            <div className='p-5'>
                <h1>Merchants List</h1>
                <Table striped bordered hover>
                    <thead> 
                        <tr>
                            <th>#</th>
                            <th>Name</th>
                            <th>Description</th>
                            <th>Email</th>
                            <th>Total Transaction Sum</th>
                        </tr>
                    </thead>
                    <tbody>
                        {
                        merchants.map(merchant => (
                            <tr>
                                <td>{merchant.id}</td>
                                <td>{merchant.name}</td>
                                <td>{merchant.description}</td>
                                <td>{merchant.email}</td>
                                <td>{merchant.totalTransactionSum}</td>
                            </tr> ))
                        }
                    </tbody>
                </Table>
            </div>
        );
    }
}

export default Merchant;