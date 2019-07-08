import React, {Component} from 'react';
import DeskService from "../service/DeskService";
import Table from "react-bootstrap/Table";
import OrderService from "../service/OrderService";

class DeskList extends Component {

    constructor(props) {
        super(props);
        this.state = {
            desks: []
        };
    }

    async componentDidMount() {
        const response = await DeskService.getAllDesks();
        console.log(response.data);
        const deskList = response.data;
        console.log(deskList[0]);

        this.setState({
            desks: response.data
        });

        console.log(this.state);
    }

    render() {
        return (
            <div className="container">
                <h3>All Desks</h3>
                <div className="container">
                    <Table striped bordered hover>
                        <thead>
                        <tr>
                            <th>Name</th>
                            <th>State</th>
                        </tr>
                        </thead>
                        <tbody>
                        {
                            this.state.desks.map(
                                desk =>
                                    <tr className="tableRow" key={desk.id}
                                        onClick={() => this.createOrder(desk.id)}>
                                        <td>{desk.name}</td>
                                        <td>{desk.deskState}</td>
                                    </tr>
                            )
                        }
                        </tbody>
                    </Table>
                </div>
            </div>
        )
    }

    async createOrder(deskId) {
        console.log("in create order deskID = " + deskId);
        const employeeId = 1;
        const response = await OrderService.createOrder(deskId, employeeId);
        const orderId = response.data.id;
        this.props.history.push('/orders/' + orderId);
    }
}

export default DeskList