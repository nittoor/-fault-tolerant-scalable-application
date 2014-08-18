-fault-tolerant-scalable-application
====================================

 fault tolerant scalable application


 a JAVA console application (use std input and output for interaction) which when ran presents an 
interactive console to the user.
User can then enter any of the following commands:
1. sort <list of space separated integers>
2. results <request id>
3. quit
sort <list of space separated integers>: 
This command should submit the request to the application and return immediately (print to the 
console), a unique request id. (It should not wait for the sort to finish)
> sort 5 4 3 2 1 //COMMENT: (user enters)
2 //COMMENT: (output = request id)
results <request id>:
This command should look for a previously submitted request with the given id and return (print to the 
console) either the status of the request if it is not yet completed or the actual results if it is.

e.g.
> sort 5 4 3 2 1
2
> results 2 
Processing
> results 2 //COMMENT: (after 20 seconds)
1 2 3 4 5


If you submit a request(e.g. sort 5 4 3 2 1), then kill the application. Then launch the application again. 
Then:
1. It should process any pending requests.
2. If you enter the results <request id>command, it should return appropriate results or the status
(same as step 1) for the request submitted before it was killed.

Run Two instances of the application simultaneously.
Submit a sort request (e.g. sort 5 4 3 2 1) in one console.
And enter “results <request id>” in the other console. This should return appropriate results or the 
status as before.

Monitoring:
1. Launch two (or n) instances of the previous application in two (or n) separate processes (so now 
we have three (or n + 1) running processes, including the supervisor).
2. Supervisor keeps monitors the two (or n) processes. If any ofthe processes are killed or exits for 
some reason it should re-launch the process.
