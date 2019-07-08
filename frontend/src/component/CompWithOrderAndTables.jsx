import React, {Component} from 'react';
import '../App.css';
import MaybeLeft from "./MaybeLeft";
import MaybeRight from "./MaybeRight";

class CompWithOrderAndTables extends Component {

    render() {
        return (
            <div className="compWithOrderAndTables">
                <MaybeLeft/>
                <MaybeRight/>
            </div>
        )
    }
}

export default CompWithOrderAndTables