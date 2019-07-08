import React, {Component} from 'react';
import '../App.css';
import {Route, Switch} from 'react-router-dom'
import ProductList from "./ProductList";
import DeskList from "./DeskList";

class MaybeRight extends Component {

    render() {
        return (
            <div className="myComp">
                <Switch>
                    <Route path="/orders/:id" exact component={ProductList}/>
                    <Route path="/" component={DeskList}/>
                    {/*<Route path="/" render={() => "No order selected"}/>*/}
                    {/*<Route path="/components" component={ComponentList}/>*/}

                </Switch>
            </div>
        )
    }
}

export default MaybeRight