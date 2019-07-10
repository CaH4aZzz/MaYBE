import React, {Component} from 'react';
import ComponentProductService from "../service/ComponentProductService";
import {ErrorMessage, Field, Form, Formik} from "formik";

class ProductComponents extends Component {

    constructor(props) {
        super(props);
        this.state = {
            id: this.props.match.params.id,
            componentId: '',
            quantity: '',
            components: []
        };
        this.refreshComponents = this.refreshComponents.bind(this);
        this.onSubmit = this.onSubmit.bind(this);
        this.validate = this.validate.bind(this);
    }

    render() {
        let {componentId, quantity} = this.state;
        return (

            <div className="container">
                <p>Recipe</p>
                <div className="container">
                    <table className="table" style={{width: '90%'}}>
                        <thead>
                        <tr>
                            <th>Name</th>
                            <th>Measure</th>
                            <th>Quantity</th>
                        </tr>
                        </thead>
                        <tbody>
                        {
                            this.state.components.map(
                                components =>
                                    <tr key={components.id}>
                                        <td>{components.component.name}</td>
                                        <td>{components.component.measure}</td>
                                        <td>{components.quantity}</td>
                                    </tr>
                            )
                        }
                        </tbody>
                    </table>
                    <div className="container">
                        <Formik
                            initialValues={{componentId, quantity}}
                            onSubmit={this.onSubmit}
                            validateOnChange={false}
                            validateOnBlur={false}
                            validate={this.validate}
                            enableReinitialize={true}>
                            {
                                (props) => (
                                    <Form>
                                        <ErrorMessage name="name" component="div"
                                                      className="alert alert-warning"/>
                                        <ErrorMessage name="quantity" component="div"
                                                      className="alert alert-warning"/>
                                        <fieldset className="form-group">
                                            <label>Component Name</label>
                                            <Field className="form-control" type="text" name="componentId"/>
                                        </fieldset>
                                        <fieldset className="form-group">
                                            <label>Quantity</label>
                                            <Field className="form-control" type="text" name="quantity"/>
                                        </fieldset>
                                        <button className="btn btn-success" type="submit">Save</button>
                                    </Form>
                                )
                            }
                        </Formik>
                    </div>
                    <div className="row">
                        <button className="btn btn-success" onClick={this.addComponentClicked}>Add</button>
                    </div>
                </div>
            </div>
        )
    }

    componentDidMount() {
        this.refreshComponents();
    }

    refreshComponents() {
        ComponentProductService.getProductComponents(this.state.id).then(
            response => {
                this.setState({components: response.data});
            }
        )
    }

    onSubmit(values) {
        let componentProductDTO = {
            componentId: values.componentId,
            quantity: values.quantity
        };
        console.log(componentProductDTO);
        console.log(this.state.id);

        ComponentProductService.addProductComponents(this.state.id, componentProductDTO)
            .then((response) => {
                console.log(response);
                this.props.history.push(`/products/${this.state.id}/components`)
            });
    }

    validate(values) {
        let errors = {};
        if (!values.componentId) {
            errors.componentId = 'Enter Component'
        }
        if (!values.quantity) {
            errors.quantity = 'Enter quantity of Component'
        }
        return errors
    }
}

export default ProductComponents