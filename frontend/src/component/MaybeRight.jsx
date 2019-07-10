import React, {Component} from 'react';
import '../App.css';
import {Route, Switch} from 'react-router-dom'
import ProductListToOrder from "./ProductListToOrder";
import DeskList from "./DeskList";

class MaybeRight extends Component {

    render() {
        return (
            <div className="myComp">
                <Switch>
                    <Route path="/" exact component={DeskList}/>
                    <Route path="/orders/:id" exact component={ProductListToOrder}/>
                    {/*<Route path="/" render={() => "No order selected"}/>*/}
                    {/*<Route path="/components" component={ComponentList}/>*/}

                </Switch>
            </div>
        )
    }
}

export default MaybeRight