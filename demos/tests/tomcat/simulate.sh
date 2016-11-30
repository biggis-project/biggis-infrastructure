#!/bin/bash

COUNTER=0
MAX=$1
SLEEP=$2
aid='aggr-001';
sid=('sens-001' 'sens-002' 'sens-003');
temp=('10.5' '11.0' '11.5' '12.0' '12.5' '13.0' '13.5' '14.0' '14.5' '15.0' '15.5' '16.0' '16.5' '17.0'
      '17.5' '18.0' '18.5' '19.0' '19.5' '20.0' '20.5' '21.0' '21.5' '22.0' '22.5' '23.0' '23.5' '24.0'
      '25.5' '26.0' '26.5' '27.0' '27.5' '28.0' '28.5' '29.0' '29.5' '30.0')
humid=('0.99' '0.98' '0.97' '0.96' '0.95' '0.94' '0.93' '0.92' '0.91' '0.90' '0.89' '0.88' '0.87' '0.86'
      '0.85' '0.84' '0.83' '0.82' '0.81' '0.80' '0.79' '0.78' '0.77' '0.76' '0.75' '0.74' '0.73' '0.72'
      '0.71' '0.70' '0.69' '0.68' '0.67' '0.66' '0.65' '0.64' '0.63' '0.62' '0.61' '0.60')

RANDOM=$$$(date +%s)

while [  $COUNTER -lt $MAX ]; do
   echo The counter is $COUNTER

   selectedAID=$aid
   selectedSID=${sid[$RANDOM % ${#sid[@]} ]}
   selectedTEMP=${temp[$RANDOM % ${#temp[@]} ]}
   selectedHUMID=${humid[$RANDOM % ${#humid[@]} ]}
   epoch=$(date +%s)

   curl -X POST "http://$3:8080/api/v1/geosensors?aid=$selectedAID&sid=$selectedSID&epoch=$epoch&temp=$selectedTEMP&humid=$selectedHUMID"

   let COUNTER=COUNTER+1
   sleep $SLEEP
done
