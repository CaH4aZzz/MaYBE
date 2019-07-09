import React, {Component} from 'react';
import '../App.css';
import {Route, Switch} from 'react-router-dom'
import CompWithOrderAndTables from "./CompWithOrderAndTables";
import Statistics from "./Statistics";
import ComponentList from "./ComponentList";
import NaviBar from "./NaviBar";
import StatisticsSummary from "./statistics/StatisticsSummary";
import StatisticsComponent from "./statistics/StatisticsComponent";
import StatisticsEmployee from "./statistics/StatisticsEmployee";
import StatisticsDesk from "./statistics/StatisticsDesk";

class SuperComponent extends Component {
    constructor(props) {
        super(props);
        this.state = {
            dateFrom: '',
            dateTo: '',
            count: 0
        }
    }

    dateFromStat = (a, b) => {
        this.state.dateFrom = a;
        this.state.dateTo = b;
    };

    render() {
        return (
            <div className="superComponent">
                <div style={{border: "black 1px solid"}}>
                    <Route path="/"
                           render={props => <NaviBar {...props} />}/>
                </div>
                <div style={{border: "black 1px solid"}}>
                    <Switch>
                        <Route path="/" exact component={CompWithOrderAndTables}/>
                        <Route path="/orders/:id" exact component={CompWithOrderAndTables}/>}/>
                        <Route path="/components" exact component={ComponentList}/>
                        <Route path="/statistics" exact
                            // render={props => <Statistics onOrderLoaded={this.dateFromStat} {...props} />}/>
                               render={props => <Statistics count={this.state.count}
                                                            increaseCount={(count) => this.setState({count})}
                                                            dateFr={this.dateFromStat} {...props}/>}/>
                        <Route path="/summary" exact render={props => <StatisticsSummary dateFrom={this.state.dateFrom}
                                                                                         dateTo={this.state.dateTo}/>}/>
                        <Route path="/component-report" exact
                               render={props => <StatisticsComponent dateFrom={this.state.dateFrom}
                                                                     dateTo={this.state.dateTo}/>}/>
                        <Route path="/employee-report" exact
                               render={props => <StatisticsEmployee dateFrom={this.state.dateFrom}
                                                                    dateTo={this.state.dateTo}/>}/>
                        <Route path="/desk-report" exact
                               render={props => <StatisticsDesk dateFrom={this.state.dateFrom}
                                                                    dateTo={this.state.dateTo}/>}/>
                    </Switch>
                </div>
            </div>
        )
    }
}

export default SuperComponent