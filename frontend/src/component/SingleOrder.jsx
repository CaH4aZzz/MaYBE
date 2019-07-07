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
            deskId: ''
        };
    }

    async componentDidMount() {
        console.log('in singleOrder' + this.state.id);

        if (this.state.id === -1) {
            console.log("id - 1?");
            return
        }

        const response = await OrderService.getOrderById(this.state.id)

        this.setState({
            id:response.data.id,
            deskId: response.data.deskId
        });

        this.props.onOrderLoaded(response.data)

    }

    render() {
        let {id, deskId} = this.state;
        return (
            <div>
                <div >
                    <p style={{float: "left", width: "50%", fontSize: "20px"}}>Order # {id}</p>
                    <p style={{float: "right", width: "50%", fontSize: "20px"}}>Table # {deskId}</p>
                </div>
                <OrderItemList id={id}/>
            </div>
        )
    }
}

export default SingleOrder