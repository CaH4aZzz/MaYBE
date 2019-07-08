import React, {Component} from 'react';
import OrderService from "../service/OrderService";
import OrderItemList from "./OrderItemList";

class SingleOrder extends Component {

    constructor(props) {
        super(props);
        console.log('single order');
        this.state = {
            id: this.props.match.params.id,
            orderItems: [],
            deskId: '',
            total: ''
        };
    }

    async componentDidMount() {
        console.log('in singleOrder' + this.state.id);

        if (this.state.id === -1) {
            console.log("id - 1?");
            return
        }

        const response = await OrderService.getOrderById(this.state.id);
        this.setState({
            id: response.data.id,
            deskId: response.data.deskId,
            total: response.data.total
        });
    }

    render() {
        let {id, deskId, total} = this.state;
        return (
            <div>
                <div>
                    <p style={{float: "left", width: "30%", fontSize: "20px"}}>Order # {id}</p>
                    <p style={{float: "right", width: "30%", fontSize: "20px"}}>Total {total}</p>
                    <p style={{float: "right", width: "30%", fontSize: "20px"}}>Table # {deskId}</p>
                </div>
                <OrderItemList id={id}/>
                <div className="row">
                    <button className="btn btn-success" onClick={() => this.props.history.goBack()}>Back</button>
                    <button className="btn btn-warning" onClick={() => this.closeOrder(id, deskId)}>Close</button>
                </div>
            </div>
        )
    }

    async closeOrder(orderId, deskId) {
        const employeeId = 1;
        const response = await OrderService.closeOrder(orderId, deskId, employeeId);
        console.log("close order " + response);
        this.props.history.push('/');
    }
}


export default SingleOrder