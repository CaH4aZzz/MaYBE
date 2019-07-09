import React, {Component} from 'react';
import Table from "react-bootstrap/Table";
import StatisticsService from "../../service/StatisticsService";

class StatisticsComponent extends Component {

    constructor(props) {
        super(props);
        this.state = {
            statisticsList: []
        };
    }

    async componentDidMount() {
        const response = await StatisticsService.getComponentReport(this.props.dateFrom, this.props.dateTo);
        this.setState({
            statisticsList: response.data
        });
    }

    render() {
        return (
            <div className="container">
                <h3>Component Statistics</h3>
                <div className="container">
                    <Table striped bordered hover>
                        <thead>
                        <tr>
                            <th>Component</th>
                            <th>Measure</th>
                            <th>IncomeQuantity</th>
                            <th>IncomeTotal</th>
                            <th>OutcomeQuantity</th>
                            <th>OutcomeTotal</th>
                        </tr>
                        </thead>
                        <tbody>
                        {
                            this.state.statisticsList.map(function (statisticsItem, index) {
                                    return (
                                        <tr className="tableRow" key={index}>
                                            <td>{statisticsItem.componentName}</td>
                                            <td>{statisticsItem.measure}</td>
                                            <td>{statisticsItem.income}</td>
                                            <td>{statisticsItem.incomeTotal}</td>
                                            <td>{statisticsItem.outcome}</td>
                                            <td>{statisticsItem.outcomeTotal}</td>
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

export default StatisticsComponent