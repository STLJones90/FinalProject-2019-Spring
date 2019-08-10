var ctx = document.getElementById('myAvgPriceChart').getContext('2d');
var ctx2 = document.getElementById('myHomeChart').getContext('2d');


var labelText = document.getElementById('chartscript').getAttribute('data-label-text');
var avgPriceDataPoints = document.getElementById('chartscript').getAttribute('avg-price-data-points').split("|");
var medPriceDataPoints = document.getElementById('chartscript').getAttribute('med-price-data-points').split("|");
var avgHomesDataPoints = document.getElementById('chartscript').getAttribute('avg-homes-data-points').split("|");
var dataLabelYear = document.getElementById('chartscript').getAttribute('data-label-year').split("|");
var dataLabelInterval = document.getElementById('chartscript').getAttribute('data-label-interval').split("|");

/*
var dataLabelMonth = document.getElementById('chartscript').getAttribute('data-label-month').split("|");
var dataLabelQuarter = document.getElementById('chartscript').getAttribute('data-label-qaurter').split("|");
*/

ctx.canvas.parentNode.style.height = '350px';
ctx.canvas.parentNode.style.width = '1500px';

ctx2.canvas.parentNode.style.height = '350px';
ctx2.canvas.parentNode.style.width = '1500px';

var myAvgPriceChart = new Chart(ctx, {
    type: 'line',
    data: {
        labels: dataLabelYear, dataLabelInterval,
        datasets: [{
            label: "Average Home Prices",
            data: avgPriceDataPoints,

            backgroundColor: [
                'rgba(255, 99, 132, .2)',
            ],
            fill: false,
            borderColor: [
                'rgba(255, 99, 132, 1)',
            ],

            borderWidth: 1,
            borderColor: 'rgba(223, 43, 43, 1)'
        },
        {
        label: "Median Home Prices",
                    data: medPriceDataPoints,

                    backgroundColor: [
                        'rgba(37, 243, 71, .2)',
                    ],
                    fill: false,
                    borderColor: [
                        'rgba(37, 243, 71, 1)',
                    ],

                    borderWidth: 1,
                    borderColor: 'rgba(37, 243, 71, 1)'
                }
        ]
    },


    options: {
    legend: {
                labels: {
                fontColor: "white",
                        }
            },
      maintainAspectRatio: false,
        scales: {
                    yAxes: [{
                        ticks: {
                            fontColor: "white",
                            fontSize: 18,
                            beginAtZero: true
                        }
                    }],
                    xAxes: [{
                        ticks: {
                            fontColor: "white",
                            fontSize: 14,
                            beginAtZero: true
                        }
                    }]
                }
            }
        });




var myHomeChart = new Chart(ctx2, {
    type: 'line',
    data: {
        labels: dataLabelYear, dataLabelInterval,
        datasets: [{
            label: "Homes Sold",
            data: avgHomesDataPoints,

            backgroundColor: [
                'rgba(37, 243, 71, 0.2)',
            ],
            fill: false,
            borderColor: [
                'rgba(255, 99, 132, 1)',
            ],

            borderWidth: 1,
            borderColor: 'rgba(37, 243, 71, 1)'
        }]
    },


    options: {
        legend: {
                    labels: {
                    fontColor: "white",
                            }
                },
    maintainAspectRatio: false,
        scales: {
                           yAxes: [{
                               ticks: {
                                   fontColor: "white",
                                   fontSize: 18,
                                   beginAtZero: true
                               }
                           }],
                           xAxes: [{
                               ticks: {
                                   fontColor: "white",
                                   fontSize: 14,
                                   beginAtZero: true
                               }
                           }]
                       }
                   }
               });







/*
var myChart = new Chart(ctx, {
var lineChartData = {
			labels: dataPointLabel,
			datasets: [{
				label: 'My First dataset',
				borderColor: window.chartColors.red,
				backgroundColor: window.chartColors.red,
				fill: false,
				data: [
					label: labelText,
                    data: avgPriceDataPoints
				],
				yAxisID: 'y-axis-1',
			}, {
				label: 'My Second dataset',
				borderColor: window.chartColors.blue,
				backgroundColor: window.chartColors.blue,
				fill: false,
				data: [
                     label: labelText,
                     data: avgHomesDataPoints
				],
				yAxisID: 'y-axis-2'
			}]
		};

		window.onload = function() {
			var ctx = document.getElementById('mychart').getContext('2d');
			window.myLine = Chart.Line(ctx, {
				data: lineChartData,
				options: {
					responsive: true,
					hoverMode: 'index',
					stacked: false,
					title: {
						display: true,
						text: 'Chart.js Line Chart - Multi Axis'
					},
					scales: {
						yAxes: [{
							type: 'linear', // only linear but allow scale type registration. This allows extensions to exist solely for log scale for instance
							display: true,
							position: 'left',
							id: 'y-axis-1',
						}, {
							type: 'linear', // only linear but allow scale type registration. This allows extensions to exist solely for log scale for instance
							display: true,
							position: 'right',
							id: 'y-axis-2',

							// grid line settings
							gridLines: {
								drawOnChartArea: false, // only want the grid lines for one axis to show up
							},
						}],
					}
				}
			});
		};

*/