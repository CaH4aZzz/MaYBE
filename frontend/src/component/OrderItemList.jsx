// @import url(https://unpkg.com/bootstrap@4.1.0/dist/css/bootstrap.min.css);
import React, {Component} from 'react';
import {withRouter} from 'react-router-dom'
import OrderItemService from "../service/OrderItemService";
import Table from "react-bootstrap/Table";

class OrderItemList extends Component {

    constructor(props) {
        super(props);
        this.state = {
            orderItems: []
        };
        this.refreshOrderItems = this.refreshOrderItems.bind(this);
    }

    componentDidMount() {
        this.refreshOrderItems();
    }

    refreshOrderItems() {
        OrderItemService.getOrderItemsById(this.props.id).then(
            response => {
                console.log(response.data.content);
                this.setState({
                    orderItems: response.data.content
                });
                console.log(this.state)
            }
        )
    }

    render() {
        return (
            <div className="container">
                <Table striped bordered hover>
                    <thead>
                    <tr>
                        <th>Name</th>
                        <th>Price</th>
                        <th>Quantity</th>
                        <th></th>
                    </tr>
                    </thead>
                    <tbody>
                    {
                        this.state.orderItems.map(function (orderItem, index) {
                                return (
                                    <tr key={index}>
                                        <td>{orderItem.productId}</td>
                                        <td>{orderItem.price}</td>
                                        <td>{orderItem.quantity}</td>
                                        <td>
                                            <button className="btn btn-success"
                                                    onClick={() => this.componentDidMount}>Update
                                            </button>
                                        </td>
                                    </tr>
                                )
                            }
                        )
                    }
                    </tbody>
                </Table>
            </div>
        )
    }
}

export default withRouter(OrderItemList)