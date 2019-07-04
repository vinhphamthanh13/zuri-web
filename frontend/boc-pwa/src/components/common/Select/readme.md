# Select and Option component

## TODO

- Make feature multiple select (Like tag)
- Option children render for custom Option

## Example

```javascript
class Test extends React.Component {
  state = {
    value: '',
  };

  render() {
    return (
      <Select
        placeholder="Fill your soul"
        filterable
        value={this.state.value}
        onChange={(name, { value, label }) => this.setState({ value })}
        name="test"
      >
        <Option label="apple" value="apple" />
        <Option label="orgen" value="orgen" />
        <Option label="banana" value="banana" disabled />
        <Option label="mango" value="mango" />
        <Option label="coconut" value="coconut" />
        <Option label="lemon" value="lemon" disabled />
        <Option label="kiwi" value="kiwi" />
        <Option label="kiwi1" value="kiwi1" />
        <Option label="kiwi2" value="kiwi2" />
        <Option label="kiwi3" value="kiwi3" />
        <Option label="kiwi4" value="kiwi4" />
        <Option label="kiwi5" value="kiwi5" />
        <Option label="kiwi6" value="kiwi6" />
        <Option label="kiwi7" value="kiwi7" />
        <Option label="kiwi8" value="kiwi8" />
        <Option label="kiwi9" value="kiwi9" />
        <Option label="kiwi10" value="kiwi10" />
      </Select>
    );
  }
}
```

## Select Props

### Select Attriable

| Attribute   | Description                                                          | Type           | Accepted Values | Default   |
| ----------- | -------------------------------------------------------------------- | -------------- | --------------- | --------- |
| value       | Value of select component                                            | number, string | --              | undefined |
| name        | Name of attribute                                                    | string         |                 | undefined |
| className   | Class this will pass through Input component                         | string         |                 | ''        |
| placeholder | Placeholder for select, this props will pass through Input component | string         |                 | ''        |
| errorMsg    | errorMsg will pass through Input component                           | string         |                 | ''        |
| disabled    | Disabled select                                                      | boolean        | true/false      | false     |
| multiple    | Support multiple mode                                                | boolean        | true/false      | false     |
| required    | required will pass through Input component                           | boolean        | true/false      | false     |
| filterable  | Support filterable mode, let user able to searching through Options  | boolean        | true/false      | false     |
| touched     | Because Input use formik so need to pass touched to it               | boolean        | true/false      | false     |
| children    | Should pass only Option Component with props descriable below        |                |                 |           |

### Select Events

| Attribute        | Description                                                                                                     | Type     | Accepted Values | Default   |
| ---------------- | --------------------------------------------------------------------------------------------------------------- | -------- | --------------- | --------- |
| onChange         | Trigger only when value of Select change (Use onVisiableChange instead for searching change in filterable mode) | Function | --              | undefined |
| onVisiableChange | Trigger when user searching in filterable mode                                                                  | Function | --              | undefined |

## Option Props

| Attribute | Description                       | Type           | Accepted Values | Default  |
| --------- | --------------------------------- | -------------- | --------------- | -------- |
| value     | Value of Option                   | string, number | ---             | Required |
| label     | Label of Option                   | string, number | ---             | Required |
| className | Class name pass to wrapper elemnt | string         | ---             | ''       |
| disabled  | Is disabled                       | boolean        | true/false      | false    |
