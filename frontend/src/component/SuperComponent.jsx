import React, {Component} from 'react';
import '../App.css';
import {Route, Switch} from 'react-router-dom'
import CompWithOrderAndTables from "./CompWithOrderAndTables";
import Statistics from "./Statistics";
import ComponentList from "./ComponentList";
import NaviBar from "./NaviBar";
import StatisticsComponent from "./statistics/StatisticsComponent";
import StatisticsDesk from "./statistics/StatisticsDesk";
import AdminNavBar from "./AdminNavBar";
import SingleComponent from "./SingleComponent";
import ProductList from "./ProductList";
import SingleProduct from "./SingleProduct";
import ProductComponents from "./ProductComponents";
import SingleInvoice from "./SingleInvoice";
import InvoiceItemsList from "./InvoiceItemsList";

class SuperComponent extends Component {
    constructor(props) {
        super(props);
        this.state = {
            dateFrom: '',
            dateTo: '',
            count: 0
        }
    }

    dateFromStat = (a, b) => {
        this.state.dateFrom = a;
        this.state.dateTo = b;
    };

    render() {
        return (
            <div className="superComponent">
                <div style={{border: "black 1px solid"}}>
                    <Route path="/"
                           render={props => <NaviBar {...props} />}/>
                    <Route path="/"
                           render={props => <AdminNavBar {...props}/>}/>
                    <Route path="/"
                           render={props => <Statistics dateFr={this.dateFromStat} {...props}/>}/>
                </div>
                <div style={{border: "black 1px solid"}}>
                    <Switch>
                        <Route path="/" exact component={CompWithOrderAndTables}/>
                        <Route path="/orders/:id" exact component={CompWithOrderAndTables}/>}/>
                        <Route path="/components" exact component={ComponentList}/>
                        <Route path="/component-report" exact
                               render={props => <StatisticsComponent dateFrom={this.state.dateFrom}
                                                                     dateTo={this.state.dateTo} {...props}/>}/>
                        <Route path="/desk-report" exact
                               render={props => <StatisticsDesk dateFrom={this.state.dateFrom}
                                                                dateTo={this.state.dateTo} {...props}/>}/>
                        <Route path="/components" exact render={props => <ComponentList {...props}/>}/>
                        <Route path="/components/:id" exact render={props => <SingleComponent {...props}/>}/>
                        <Route path="/products" exact render={props => <ProductList {...props}/>}/>
                        <Route path="/products/:id" exact render={props => <SingleProduct {...props}/>}/>
                        <Route path="/products/:id/components" exact render={props => <ProductComponents {...props}/>}/>
                        <Route path="/invoices/:id" exact render={props => <SingleInvoice {...props}/>}/>
                        <Route path="/invoices/:id/invoiceItmes" exact
                               render={props => <InvoiceItemsList {...props}/>}/>
                    </Switch>
                </div>
            </div>
        )
    }
}

export default SuperComponent