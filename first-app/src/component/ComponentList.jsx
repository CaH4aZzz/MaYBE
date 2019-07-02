import React, {Component} from 'react'
import ComponentsDataService from "../service/ComponentsDataService"

class ComponentList extends Component {

    constructor(props) {
        super(props)
        this.state = {
            components: [],
            message: null
        }
        this.refreshComponents = this.refreshComponents.bind(this)
        this.updateComponentClicked = this.updateComponentClicked.bind(this)
        this.addComponentClicked = this.addComponentClicked.bind(this)
    }

    componentDidMount() {
        console.log("refresh")
        this.refreshComponents();
    }

    refreshComponents() {
        ComponentsDataService.retrieveAllComponents().then(
            response => {
                this.setState({components: response.data.content});
            }
        )
    }

    updateComponentClicked(id) {
        console.log('update Component ' + id);
        // eslint-disable-next-line no-template-curly-in-string
        this.props.history.push('/${id}')
        console.log(id)
    }

    addComponentClicked() {
        console.log('add clicked')
        this.props.history.push('/components/-1')
    }

    render() {
        return (
            <div className="container">
                <h3>All Components</h3>
                <div className="container">
                    <table className="table">
                        <thead>
                        <tr>
                            <th>Name</th>
                            <th>Quantity</th>
                            <th>Measure</th>
                            <th>Total</th>
                        </tr>
                        </thead>
                        <tbody>
                        {
                            this.state.components.map(
                                component =>
                                    <tr key={component.id}>
                                        <td>{component.name}</td>
                                        <td>{component.quantity}</td>
                                        <td>{component.measure}</td>
                                        <td>{component.total}</td>
                                        <td>
                                            <button className="btn btn-success"
                                                    onClick={() => this.updateComponentClicked(component.id)}>Update
                                            </button>
                                        </td>
                                    </tr>
                            )
                        }
                        </tbody>
                    </table>
                    <div className="row">
                        <button className="btn btn-success" onClick={this.addComponentClicked}>Add</button>
                    </div>
                </div>
            </div>
        )
    }


}

export default ComponentList