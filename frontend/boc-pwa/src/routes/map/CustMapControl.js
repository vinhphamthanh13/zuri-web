import { Component } from 'react';
import { createPortal } from 'react-dom';
import { MAP } from 'react-google-maps/lib/constants';
import { number, string, element, object, oneOfType, array } from 'prop-types';

/**
 * This Component for add custom control to map
 * (map.controls[position].push(component))
 * NOTE:
 * Can ref to map through context in constructor (or this.context expect contructor)
 * User constructor to add div and render will createPortal
 */
export default class CustMapControl extends Component {
  static propTypes = {
    position: number.isRequired,
    children: oneOfType([element, array]),
    className: string,
  };

  static defaultProps = {
    children: [],
    className: '',
  };

  static contextTypes = { [MAP]: object };

  constructor(props, context) {
    super(props);

    this.map = context[MAP];
    this.controlDiv = document.createElement('div');
    this.divIndex = this.map.controls[this.props.position].length;
    this.map.controls[props.position].push(this.controlDiv);
  }

  componentWillUnmount() {
    this.map.controls[this.props.position].removeAt(this.divIndex);
  }

  render() {
    const { className } = this.props;
    className && this.controlDiv.classList.add(className);

    return createPortal(this.props.children, this.controlDiv);
  }
}
