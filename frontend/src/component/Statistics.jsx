import React, {Component} from 'react';
import StatisticsService from "../service/StatisticsService";


class Statistics extends Component {

    constructor(props) {
        super(props);
        this.state = {
            dateFrom: '',
            dateTo: ''
        };
        this.publish = this.publish.bind(this);
        this.handleChange = this.handleChange.bind(this);
        this.getSummary = this.getSummary.bind(this);
    }

    render() {
        return (
            <div className="container">
                <div className="statButtons">
                    <input
                        type="text"
                        name="dateFrom"
                        placeholder="Enter topic here..."
                        value={this.state.dateFrom}
                        onChange={this.handleChange}
                    />
                    <input
                        type="text"
                        name="dateTo"
                        placeholder="Enter payload here..."
                        value={this.state.dateTo}
                        onChange={this.handleChange}
                    />
                    <button onClick={() => this.getSummary()}>Summary</button>
                    <button onClick={() => this.getProductStat()}>Product Stat</button>
                    <button onClick={() => this.getComponentStat()}>Component Stat</button>
                    <button onClick={() => this.getEmployeeStat()}>Employee Stat</button>
                </div>
            </div>
        )
    }

    publish() {
        console.log(this.state.dateFrom, this.state.dateTo);
    }

    handleChange({target}) {
        this.setState({
            [target.name]: target.value
        });
    }

    async getSummary() {
        console.log("getSummary clicked");
        this.publish();
        const response = await StatisticsService.getSummaryReport(this.state.dateFrom, this.state.dateTo);
        console.log(response.data);
    }

    async getComponentStat() {
        console.log("getComponents stat");
        this.publish();
        const response = await StatisticsService.getComponentReport(this.state.dateFrom, this.state.dateTo);
        console.log(response.data);
    }

    async getProductStat() {
        console.log("getProducts stat");
        this.publish();
        const response = await StatisticsService.getProductReport(this.state.dateFrom, this.state.dateTo);
        console.log(response.data);
    }

    async getEmployeeStat() {
        console.log("getEmployee stat");
        this.publish();
        const response = await StatisticsService.getEmployeeReport(this.state.dateFrom, this.state.dateTo);
        console.log(response.data);
    }
}

export default Statistics