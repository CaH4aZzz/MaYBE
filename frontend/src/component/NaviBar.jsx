import React, {Component} from 'react';

class NaviBar extends Component {
    constructor(props) {
        super(props);
        this.goToStatistics = this.goToStatistics.bind(this);
    }

    async componentDidMount() {
        console.log("in navy bar " + this.state);
        console.log(this.props);
        this.setState({})
    }

    async goToStatistics() {
        console.log("in goToStatistics");
        this.props.history.push('/statistics');
    }

    async goHome() {
        console.log("in home");
        this.props.history.push('/');
    }

    render() {
        return (
            <div className="container">
                <h1 onClick={() => this.goHome()}>Maybe</h1>
                <button className="btn btn-primary" onClick={() => this.goToStatistics()}>Statistics</button>
                <button className="btn btn-primary">Admin</button>
            </div>
        )
    }
}

export default NaviBar