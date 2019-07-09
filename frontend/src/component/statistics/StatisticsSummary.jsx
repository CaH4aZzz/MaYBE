import React, {Component} from 'react';
import Table from "react-bootstrap/Table";
import StatisticsService from "../../service/StatisticsService";

class StatisticsSummary extends Component {

    constructor(props) {
        super(props);
        this.state = {
            statisticsList: []
        };
    }

    async componentDidMount() {
        const response = await StatisticsService.getSummaryReport(this.props.dateFrom, this.props.dateTo);
        this.setState({
            statisticsList: response.data
        });
        console.log(this.state.statisticsList.date);
    }

    render() {
        return (
            <div className="container">
                <h3>Summary</h3>
                <div className="container">
                    <Table striped bordered hover>
                        <thead>
                        <tr>
                            <th>Date</th>
                            <th>Income</th>
                            <th>Outcome</th>
                        </tr>
                        </thead>
                        <tbody>
                        {
                            this.state.statisticsList.map(function (statisticsItem, index) {
                                    return (
                                        <tr className="tableRow" key={index}>
                                            <td>{parseDate(statisticsItem.date)}</td>
                                            <td>{statisticsItem.income}</td>
                                            <td>{statisticsItem.outcome}</td>
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

function parseDate(date) {

    let newDate = date[0] + "-";
    newDate += getNextDateItem(date[1], true);
    newDate += getNextDateItem(date[2], false);
    return newDate;
}

function getNextDateItem(date, isNotLast){
    let newDate = '';
    if (date.length === 2) {
        newDate += date;
        if(isNotLast) newDate += "-";
    } else {
        newDate += '0' + date;
        if(isNotLast) newDate += "-";
    }
    return newDate;
}

export default StatisticsSummary
