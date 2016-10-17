#!/bin/bash
# hit
curl -so /dev/null -w '%{time_total}\n' "http://localhost:8088/api/direct?dep_sid=655840&arr_sid=907858"
# miss
curl -so /dev/null -w '%{time_total}\n' "http://localhost:8088/api/direct?dep_sid=40&arr_sid=9858"
# hit
curl -so /dev/null -w '%{time_total}\n' "http://localhost:8088/api/direct?dep_sid=548125&arr_sid=3338"
# miss
curl -so /dev/null -w '%{time_total}\n' "http://localhost:8088/api/direct?dep_sid=45540&arr_sid=1"
# hit
curl -so /dev/null -w '%{time_total}\n' "http://localhost:8088/api/direct?dep_sid=78399&arr_sid=1"
# miss
curl -so /dev/null -w '%{time_total}\n' "http://localhost:8088/api/direct?dep_sid=78399&arr_sid=2"
# hit
curl -so /dev/null -w '%{time_total}\n' "http://localhost:8088/api/direct?dep_sid=78399&arr_sid=1"
# miss
curl -so /dev/null -w '%{time_total}\n' "http://localhost:8088/api/direct?dep_sid=78399&arr_sid=2"
# hit
curl -so /dev/null -w '%{time_total}\n' "http://localhost:8088/api/direct?dep_sid=10659&arr_sid=77111"
# miss
curl -so /dev/null -w '%{time_total}\n' "http://localhost:8088/api/direct?dep_sid=789&arr_sid=24255"
