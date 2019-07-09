import React, {Component} from 'react';
import Table from "react-bootstrap/Table";
import StatisticsService from "../../service/StatisticsService";

class StatisticsEmployee extends Component {

    constructor(props) {
        super(props);
        this.state = {
            statisticsList: []
        };
    }

    async componentDidMount() {
        const response = await StatisticsService.getEmployeeReport(this.props.dateFrom, this.props.dateTo);
        this.setState({
            statisticsList: response.data
        });
        console.log(response);
    }

    render() {
        return (
            <div className="container">
                <h3>Employee Statistics</h3>
                <div className="container">
                    <Table striped bordered hover>
                        <thead>
                        <tr>
                            <th>Employee</th>
                            <th>OrderCount</th>
                            <th>Total</th>
                            <th>AverageBill</th>
                        </tr>
                        </thead>
                        <tbody>
                        {
                            this.state.statisticsList.map(function (statisticsItem, index) {
                                    return (
                                        <tr className="tableRow" key={index}>
                                            <td>{statisticsItem.employeeName}</td>
                                            <td>{statisticsItem.orderCount}</td>
                                            <td>{statisticsItem.total}</td>
                                            <td>{statisticsItem.averageBill}</td>
                                        </tr>
                                    )
                                }
                            )
                        }
                        </tbody>
                    </Table>
                </div>
            </div>
        )
    }

}

export default StatisticsEmployee