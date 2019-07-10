import React, {Component} from 'react';
import ProductService from "../service/ProductService";

class ProductList extends Component {

    constructor(props) {
        super(props);
        this.state = {
            products: []
        };
        this.refreshPropducts = this.refreshPropducts.bind(this);
        this.updateProductClicked = this.updateProductClicked.bind(this);
        this.addProductClicked = this.addProductClicked.bind(this);
    }

    componentDidMount() {
        this.refreshPropducts();
    }

    refreshPropducts() {
        ProductService.getAllProducts().then(
            response => {
                console.log(response.data.content);
                this.setState({products: response.data.content});
            }
        )
    }

    updateProductClicked(id) {
        console.log("update product id = ", id);
        this.props.history.push('/products/' + id);
    }

    addProductClicked() {
        console.log('add clicked');
        this.props.history.push('/products/-1')
    }

    render() {
        return (
            <div className="container">
                <h3>All Products</h3>
                <div className="container">
                    <table className="table" style={{width: '90%'}}>
                        <thead>
                        <tr>
                            <th>Name</th>
                            <th>Price</th>
                        </tr>
                        </thead>
                        <tbody>
                        {
                            this.state.products.map(
                                product =>
                                    <tr key={product.id}>
                                        <td>{product.name}</td>
                                        <td>{product.price}</td>
                                        <td>
                                            <button className="btn btn-success"
                                                    onClick={() => this.updateProductClicked(product.id)}>Update
                                            </button>
                                        </td>
                                    </tr>
                            )
                        }
                        </tbody>
                    </table>
                    <div className="row">
                        <button className="btn btn-success" onClick={this.addProductClicked}>Add</button>
                    </div>
                </div>
            </div>
        )
    }
}

export default ProductList