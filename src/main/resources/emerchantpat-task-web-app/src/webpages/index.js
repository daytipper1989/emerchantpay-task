import React from 'react';
import {
  BrowserRouter as Router,
  Switch,
  Routes,
  Route,
  Link
} from "react-router-dom";
import Home from './home';
import Merchant from './merchants';
import Transaction from './transactions';
const Webpages = () => {
    return(
        <Router>
            <Routes>
                <Route exact path="/" element= {<Home/>} />
                <Route path = "/merchants" element = {<Merchant/>} />
                <Route path = "/transactions" element = {<Transaction/>} />
            </Routes>
        </Router>
    );
};
export default Webpages;