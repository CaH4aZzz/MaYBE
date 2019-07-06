import React, {Component} from 'react';
import DeskService from "../service/DeskService";
import Table from "react-bootstrap/Table";

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
        })

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
                                    <tr key={desk.id}>
                                        <td>{desk.name}</td>
                                        <td>{desk.deskState}</td>
                                    </tr>
                            )
                        }
                        </tbody>
                    </Table>
                    <div className="row">
                        <button className="btn btn-success" onClick={() => this.props.history.goBack()}>Back</button>
                    </div>
                </div>
            </div>
        )
    }
}

export default DeskList