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
    }

    render() {
        return (
            <div className="container">
                <div className="statButtons">
                    <input
                        type="text"
                        name="dateFrom"
                        placeholder="Date from"
                        value={this.state.dateFrom}
                        onChange={this.handleChange}
                    />
                    <input
                        type="text"
                        name="dateTo"
                        placeholder="Date to"
                        value={this.state.dateTo}
                        onChange={this.handleChange}
                    />
                    <button className="btn btn-primary" onClick={() => this.getComponentStat()}>Component Stat</button>
                    <button className="btn btn-primary" onClick={() => this.getDeskStat()}>Desk Stat</button>
                </div>
            </div>
        )
    }

    async getComponentStat() {
        this.props.dateFr(this.state.dateFrom, this.state.dateTo);
        this.props.history.push('/component-report');
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