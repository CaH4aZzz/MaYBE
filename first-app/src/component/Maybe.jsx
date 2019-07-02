import React, {Component} from 'react';
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom'
import ComponentList from './ComponentList';
import SingleComponent from "./SingleComponent";

class Maybe extends Component {
    render() {
        return (
            <Router>
                <>
                    <h1>Restaurant Application</h1>
                    <Switch>
                        <Route path="" exact component={ComponentList} />
                        <Route path="/components" exact component={ComponentList} />
                        <Route path="/components/:id" component={SingleComponent} />
                    </Switch>
                </>
            </Router>
        )
    }
}

export default Maybe