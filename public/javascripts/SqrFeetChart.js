var ctx = document.getElementById('mySqrFeetChart').getContext('2d');

var labelText = document.getElementById('chartscript').getAttribute('data-label-text');
var homeSqrFeetDataPoint = document.getElementById('chartscript').getAttribute('data-points-home-sqrft')
var lotSqrFeetDataPoint = document.getElementById('chartscript').getAttribute('data-points-lot-sqrft')

ctx.canvas.parentNode.style.height = '300px';
ctx.canvas.parentNode.style.width = '700px';

var mySqrFeetChart = new Chart(ctx, {
    type: 'horizontalBar',
    data: {
        labels: labelText,
        datasets: [{
            label: "Home Square Feet",
            data: [homeSqrFeetDataPoint],

            backgroundColor: [
                'rgba(114, 0, 255, 1)',
            ],
            fill: false,
            borderColor: [
                'rgba(114, 0, 255, 1)',
            ],

            borderWidth: 1,
            borderColor: 'rgba(94, 1, 208, 1)'
        },
        {
        label: "Lot Square Feet",
                    data: [lotSqrFeetDataPoint],

                    backgroundColor: [
                        'rgba(37, 243, 71, 1)',
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


    options:  {

    elements: {
    	rectangle:  {
    	borderWidth: 1,
    				}
    		  },
    		  responsive: true,
    		  legend: {
    		  labels: {
                              fontColor: "white",
                                      }
                      },
      maintainAspectRatio: false,
        scales: {
            xAxes: [{
                ticks: {
                 fontColor: "white",
                 fontSize: 14,
                    beginAtZero: true,
                }
            }]
        }
}
}
)
;

