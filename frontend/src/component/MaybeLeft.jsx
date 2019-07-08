import React, {Component} from 'react';
import '../App.css';
import {Route, Switch} from 'react-router-dom'
import OrderList from "./OrderList";
import SingleOrder from "./SingleOrder";

class MaybeLeft extends Component {

    constructor(props) {
        super(props);
        this.state = {
            orderId: ''
        }
    }

    logOrder = (order) => {
        console.log('order from inside', order, order.deskId);
        this.setState({orderId: order.id})
    };

    render() {
        return (
            <div className="myComp">
                <Switch>
                    <Route path="/" exact component={OrderList}/>
                    <Route path="/components/:id" exact component={OrderList}/>
                    <Route path="/orders/:id"
                           render={props => <SingleOrder onOrderLoaded={this.logOrder} {...props} />}/>
                </Switch>
            </div>
        )
    }
}

export default MaybeLeft



