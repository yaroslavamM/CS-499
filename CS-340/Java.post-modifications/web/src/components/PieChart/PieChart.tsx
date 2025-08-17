import React from 'react';
import type {Animal} from "../../types";
import ReactApexChart from 'react-apexcharts';

type PieChartProps = {
  animals: Animal[]
}

export const PieChart = ({animals}: PieChartProps) => {
  if (!animals) {
    return null;
  }
  const breedsByNumber = animals.reduce((map, animal) => {
    map[animal.breed] = (map[animal.breed] || 0) + 1;
    return map;
  }, {} as Record<string, number>);
  const labels = Object.keys(breedsByNumber);
  const data = Object.values(breedsByNumber);
  return (
    <div>
      <ReactApexChart options={{
        chart: {
          type: 'pie',
        },
        labels: labels,
      }} series={data} type="pie" width={380}  />
    </div>
  );
};
