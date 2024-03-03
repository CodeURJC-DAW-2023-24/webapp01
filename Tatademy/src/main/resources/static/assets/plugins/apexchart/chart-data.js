'use strict';

$(document).ready(function() {
	function generateData(baseval, count, yrange) {


		var i = 0;
		var series = [];
		while (i < count) {
			var x = Math.floor(Math.random() * (750 - 1 + 1)) + 1;;
			var y = Math.floor(Math.random() * (yrange.max - yrange.min + 1)) + yrange.min;
			var z = Math.floor(Math.random() * (75 - 15 + 1)) + 15;

			series.push([x, y, z]);
			baseval += 86400000;
			i++;
		}
		return series;
	}
		
		fetch('/cursesMonth')
		.then(response => response.json())
		.then(data => {
			console.log('Courses per month until Octuber (include):', data);
			if($('#instructor_chart').length > 0) {
			

				var options = {
						series: [{
							name: "Cursos creados: ",
							data: data
						},
					],
					colors: ['#FF9364'],
					chart: {
					height: 300,
					type: 'area',
					toolbar: {
							show: false
						},
					zoom: {
						enabled: false
					}
					},
					markers: {
						size: 3,
					},
					dataLabels: {
					enabled: false
					},
					stroke: {
					curve: 'smooth',
					width: 3,
					},
					legend: {
						position: 'top',
						horizontalAlign: 'right',
					},
					grid: {
					show: false,
					},
					yaxis: {
						axisBorder: {
							show: true,
						},
					},
					xaxis: {
					categories:['Enero', 'Febrero', 'Marzo', 'Abril', 'Mayo', 'Junio', 'Julio', 'Agosto', 'Septiembre', 'Octubre']
						}
				};
		
				var chart = new ApexCharts(document.querySelector("#instructor_chart"), options);
				chart.render();
				}
		})
		.catch(error => console.error('Error al obtener el arreglo:', error));



		// ----------------------  Simple Column  -------------------------
		fetch('/reviewsMonth')
		.then(response => response.json())
		.then(data => {
			console.log('Review per month until Septembre (include):', data);

			if($('#order_chart').length > 0) {
				var sCol = {
					chart: {
						height: 350,
						type: 'bar',
						toolbar: {
						  show: false,
						}
					},
					plotOptions: {
						bar: {
							horizontal: false,
							columnWidth: '20%',
							endingShape: 'rounded', 
							startingShape: 'rounded'  
						},
					},
					 colors: ['#1D9CFD'],
					dataLabels: {
						enabled: false
					},
					stroke: {
						show: true,
						width: 2,
						colors: ['transparent']
					},
					series: [{
						name: 'Revenue',
						data: data
					}],
					xaxis: {
						categories: ['Enero', 'Febrero', 'Marzo', 'Abril', 'Mayo', 'Junio', 'Julio', 'Agosto', 'Septiembre'],
					},
					fill: {
						opacity: 1
			
					},
					tooltip: {
						y: {
							formatter: function (val) {
								return "Num. de resennas hechas: " + val 
							}
						}
					}
				}
			
				var chart = new ApexCharts(
					document.querySelector("#order_chart"),
					sCol
				);
			
				chart.render();
				}

		})
		.catch(error => console.error('Error al obtener el arreglo:', error));
	
	
	
		
	//});
	
	
	
});