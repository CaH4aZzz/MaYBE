import React, {Component} from 'react';


class Statistics extends Component {

    constructor(props) {
        super(props);
        this.state = {
            dateFrom: '',
            dateTo: '',
            dateFr: this.props.dateFr
        };
        this.publish = this.publish.bind(this);
        this.handleChange = this.handleChange.bind(this);
        this.getSummary = this.getSummary.bind(this);
    }

    render() {
        return (
            <div className="container">
                <div className="statButtons" style={{border: "solid black 1px"}}>
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
                    {/*<button onClick={() => this.getProductStat()}>Product Stat</button>*/}
                    <button onClick={() => this.getComponentStat()}>Component Stat</button>
                    <button onClick={() => this.getEmployeeStat()}>Employee Stat</button>
                    <button onClick={() => this.getDeskStat()}>Desk Stat</button>
                </div>
            </div>
        )
    }

    async getSummary() {
        this.props.dateFr(this.state.dateFrom, this.state.dateTo);
        this.props.history.push('/summary');
    }

    async getComponentStat() {
        this.props.dateFr(this.state.dateFrom, this.state.dateTo);
        this.props.history.push('/component-report');
    }

    async getEmployeeStat() {
        this.props.dateFr(this.state.dateFrom, this.state.dateTo);
        this.props.history.push('/employee-report');
    }

    async getDeskStat() {
        this.props.dateFr(this.state.dateFrom, this.state.dateTo);
        this.props.history.push('/desk-report');
    }

    publish() {
        console.log(this.state.dateFrom, this.state.dateTo);
    }

    handleChange({target}) {
        this.setState({
            [target.name]: target.value
        });
    }


}

export default Statistics