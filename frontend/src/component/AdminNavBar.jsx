import React, {Component} from 'react';

class AdminNavBar extends Component {

    render() {
        return (
            <div className="container">
                <div className="statButtons">
                    <button className="btn btn-primary" onClick={() => this.getAllComponents()}>Components</button>
                    <button className="btn btn-primary" onClick={() => this.getAllProducts()}>Products</button>
                    <button className="btn btn-primary" onClick={() => this.createNewInvoice()}>New invoice</button>
                </div>
            </div>
        )
    }

    getAllComponents() {
        console.log("get com clicked");
        this.props.history.push('/components');
    }

    getAllProducts() {
        console.log("get propduct clicked");
        this.props.history.push('/products');
    }

    createNewInvoice() {
        console.log("new invoice");
        this.props.history.push('/invoices/-1')
    }
}

export default AdminNavBar