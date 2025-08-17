import React, {useEffect, useState} from 'react';
import {Form, Table} from "react-bootstrap";
import type {Animal} from "./types";
import {PieChart} from "./components";

export const Application = () => {
  const [data, setData] = useState<Animal[]>([])
  const [filter, setFilter] = useState<string>('none')
  const [page, setPage] = useState(0)

  useEffect(() => {
    const typeFilter = filter === 'none' ? '' : `&typeFilter=${filter}`;
    fetch(`/api/animals?page=${page}&size=5${typeFilter}`).then(data => data.json()).then(json => {
      console.log(json)
      setData(json.content)
    })
  }, [filter, page]);

  return (
    <div className="container">
      <h2>SNHU CS-340 Dashboard by Yaroslava Meleshkevich</h2>
      <Form>
        <Form.Check id="o1" type="radio" name="filter" inline={true} checked={filter === 'WATER_RESCUE'}
                    label="Water Rescue"
                    onChange={() => setFilter('WATER_RESCUE')}/>
        <Form.Check id="o2" type="radio" name="filter" inline={true} checked={filter === 'MOUNTAIN_RESCUE'}
                    label="Mountain Rescue"
                    onChange={() => setFilter('MOUNTAIN_RESCUE')}/>
        <Form.Check id="o3" type="radio" name="filter" inline={true} checked={filter === 'DISASTER_RESCUE'}
                    label="Disaster Rescue"
                    onChange={() => setFilter('DISASTER_RESCUE')}/>
        <Form.Check id="o4" type="radio" name="filter" inline={true} checked={filter === 'none'} label="Reset"
                    onChange={() => setFilter('none')}/>
      </Form>
      <Table bordered={true} hover={true} striped={true}>
        <thead>
        <tr>
          <td>ID</td>
          <td>Name</td>
          <td>Type</td>
          <td>Breed</td>
          <td>Color</td>
          <td>Date of Birth</td>
          <td>Sex</td>
          <td>Location</td>
          <td>Check In Date</td>
          <td>Check Out Date</td>
          <td>Outcome</td>
          <td>Outcome Description</td>
        </tr>
        </thead>
        <tbody>
        {data.map(animal => (
          <tr key={animal.id}>
            <td>{animal.id}</td>
            <td>{animal.name}</td>
            <td>{animal.type}</td>
            <td>{animal.breed}</td>
            <td>{animal.color}</td>
            <td>{animal.dateOfBirth}</td>
            <td>{animal.sex}</td>
            <td>{`${animal.location.latitude}, ${animal.location.longitude}`}</td>
            <td>{animal.checkInDate}</td>
            <td>{animal.checkOutDate}</td>
            <td>{animal.outcome}</td>
            <td>{animal.outcomeDescription}</td>
          </tr>
        ))}
        </tbody>
      </Table>
      {filter !== 'none' && (<PieChart animals={data} />)}
    </div>
  )
    ;
};
