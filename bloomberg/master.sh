curl -X POST https://54.174.49.59/request/blp/refdata/HistoricalData --cacert ./bloomberg.crt --cert ./mhacks_spring_2015_058.crt --key ./mhacks_spring_2015_058.key --data @- <<EOF
{ "securities": ["IBM US Equity", "AAPL US Equity", "MSFT US Equity", "GOOG US Equity", "T US Equity", "AMZN US Equity"],
  "fields": ["PX_LAST", "OPEN"],
  "startDate": "20141201",
  "endDate": "20150117",
  "periodicitySelection": "DAILY" }
EOF
