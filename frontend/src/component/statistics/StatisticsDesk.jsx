import React, {Component} from 'react';
import Table from "react-bootstrap/Table";
import StatisticsService from "../../service/StatisticsService";

class StatisticsDesk extends Component {

    constructor(props) {
        super(props);
        this.state = {
            statisticsList: []
        };
    }

    async componentDidMount() {
        console.log("desk rep");
        const response = await StatisticsService.getDeskReport(this.props.dateFrom, this.props.dateTo);
        this.setState({
            statisticsList: response.data
        });
        console.log(response);
    }

    render() {
        return (
            <div className="container">
                <h3>Table statistics </h3>
                <div className="container">
                    <Table striped bordered hover>
                        <thead>
                        <tr>
                            <th>Table</th>
                            <th>Order Count</th>
                            <th>Revenue</th>
                        </tr>
                        </thead>
                        <tbody>
                        {
                            this.state.statisticsList.map(function (statisticsItem, index) {
                                    return (
                                        <tr className="tableRow" key={index}>
                                            <td>{statisticsItem.name}</td>
                                            <td>{statisticsItem.ordersNumber}</td>
                                            <td>{statisticsItem.revenue}</td>
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

export default StatisticsDesk