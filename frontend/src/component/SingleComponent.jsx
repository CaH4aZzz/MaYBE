import React, {Component} from 'react';
import ComponentsDataService from "../service/ComponentsDataService";
import {ErrorMessage, Field, Form, Formik} from "formik";

class SingleComponent extends Component {

    constructor(props) {
        super(props);
        console.log('single');
        this.state = {
            id: this.props.match.params.id,
            name: '',
            quantity: '',
            measure: '',
            total: ''
        };
        this.onSubmit = this.onSubmit.bind(this);
        this.validate = this.validate.bind(this);
    }

    componentDidMount() {
        console.log('in singleComp' + this.state.id);

        if (this.state.id == -1) {
            console.log("id - 1?");
            return
        }

        ComponentsDataService.retrieveComponentById(this.state.id)
            .then(response => {
                    this.setState({
                        id: response.data.id,
                        name: response.data.name,
                        quantity: response.data.quantity,
                        measure: response.data.measure,
                        total: response.data.total
                    })
                }
            )
    }

    onSubmit(values) {
        let componentDTO = {
            name: values.name,
            measure: values.measure
        };
        console.log(componentDTO);

        if (this.state.id == -1) {
            ComponentsDataService.createComponent(componentDTO)
                .then(() => this.props.history.push('/components'));
            console.log('inside if => POST')
        } else {
            ComponentsDataService.updateComponent(this.state.id, componentDTO)
                .then(() => this.props.history.push('/components'));
            console.log('inside else => PUT');
        }

    }

    validate(values) {
        let errors = {};
        if (!values.name) {
            errors.name = 'Enter Component name'
        }
        if (!values.quantity) {
            errors.quantity = 'Enter quantity of Component'
        }
        return errors
    }

    render() {
        let {id, name, quantity, measure, total} = this.state;

        return (
            <div>
                <h3>Component</h3>
                <div className="container">
                    <Formik
                        initialValues={{id, name, quantity, measure, total}}
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
                                        <label>Id</label>
                                        <Field className="form-control" type="text" name="id" disabled/>
                                    </fieldset>
                                    <fieldset className="form-group">
                                        <label>Name</label>
                                        <Field className="form-control" type="text" name="name"/>
                                    </fieldset>
                                    <fieldset className="form-group">
                                        <label>Quantity</label>
                                        <Field className="form-control" type="text" name="quantity"/>
                                    </fieldset>
                                    <fieldset className="form-group">
                                        <label>Measure</label>
                                        <Field className="form-control" type="text" name="measure"/>
                                    </fieldset>
                                    <fieldset className="form-group">
                                        <label>Total</label>
                                        <Field className="form-control" type="text" name="total"/>
                                    </fieldset>
                                    <button className="btn btn-success" type="submit">Save</button>
                                </Form>
                            )
                        }
                    </Formik>

                </div>
            </div>
        )
    }
}

export default SingleComponent