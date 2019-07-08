import React, {Component} from 'react';
import ComponentsDataService from "../service/ComponentsDataService"

class ComponentList extends Component {

    constructor(props) {
        super(props);
        this.state = {
            components: []
        };
        this.refreshComponents = this.refreshComponents.bind(this);
        this.updateComponentClicked = this.updateComponentClicked.bind(this);
        this.addComponentClicked = this.addComponentClicked.bind(this);
    }

    componentDidMount() {
        this.refreshComponents();
    }

    refreshComponents() {
        ComponentsDataService.retrieveAllComponents().then(
            response => {
                console.log(response.data.content);
                this.setState({components: response.data.content});
            }
        )
    }

    updateComponentClicked(id) {
        this.props.history.push('/components/' + id);
    }

    addComponentClicked() {
        console.log('add clicked');
        this.props.history.push('/components/-1')
    }

    render() {
        return (
            <div className="container">
                <h3>All Components</h3>
                <div className="container">
                    <table className="table" style={{width: '90%'}}>
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