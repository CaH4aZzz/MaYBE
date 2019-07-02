import React, { Component } from 'react'
import ComponentsDataService from "../service/ComponentsDataService";

class SingleComponent extends Component {

    constructor(props){
        super(props)

        this.state={
            id: this.props.match.params.id,
            name: ''
        }
    }

    componentDidMount() {
        console.log('in singleComp' + this.state.id)

        if(this.state.id === -1){
            return
        }

        ComponentsDataService.retrieveComponentById(this.state.id)
            .then(response => this.setState({
                name: response.data.name
            }))

    }

    render() {
        let {name, id} = this.state

        return(
            <div>
                <h3>Component</h3>
                <div>{id}</div>
                <div>{name}</div>
            </div>
        )
    }
}
export default SingleComponent