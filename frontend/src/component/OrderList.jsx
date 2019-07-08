import React, {Component} from 'react';
import OrderService from "../service/OrderService";
import Table from "react-bootstrap/Table";

class OrderList extends Component {

    constructor(props) {
        super(props);
        this.state = {
            orders: []
        };
        this.refreshOrders = this.refreshOrders.bind(this);
        this.updateOrderClicked = this.updateOrderClicked.bind(this);
        // this.addComponentClicked = this.addComponentClicked.bind(this);
    }

    updatePage = () => {
    };

    componentDidMount() {
        this.refreshOrders();
    }

    refreshOrders() {
        OrderService.getAllOrders().then(
            response => {
                this.setState({orders: response.data.content});
            }
        )
    }

    updateOrderClicked(id) {
        console.log('update Order ' + id);
        this.props.history.push('/orders/' + id);
    }

    render() {
        return (

            <div className="container">
                <h3>All Orders</h3>
                <div className="container">
                    <Table striped bordered hover>
                        <thead>
                        <tr>
                            <th>ID</th>
                            <th>Desk</th>
                            <th>Employee</th>
                        </tr>
                        </thead>
                        <tbody>
                        {
                            this.state.orders.map(
                                order =>
                                    <tr className="tableRow" key={order.id}
                                        onClick={() => this.updateOrderClicked(order.id)}>
                                        <td>{order.id}</td>
                                        <td>{order.deskId}</td>
                                        <td>{order.employeeId}</td>
                                    </tr>
                            )
                        }
                        </tbody>
                    </Table>
                </div>
            </div>
        )
    }
}

export default OrderList