# VoteApp
Restful App in Spring boot which has the following functionalities:

1. Adds a vote for candidate
Takes a request in the following format and adds the vote for candidate
  {
"name":"Ram",
"candidateId":"1"
}
Name: [String type] Takes either Ram or Sham
candidateId: [String type] Takes "1" for Ram and "2" for Sham

URL: http://localhost:8080/add
Body:   {
"name":"Ram",
"candidateId":"1"
}

2. Get number of votes/count by name
URL: http://localhost:8080/get/Sham

Takes name of the candidate as url parameter
Returns Name and Number of votes
 {
    "SHAM": 8
}

3. Get total votes for candidates
URL: http://localhost:8080/totalcount

Takes name of the candidate as url parameter
Returns Name and Number of votes for all candidates
 [
    {
        "name": "Ram",
        "count": "7"
    },
    {
        "name": "Sham",
        "count": "9"
    }
]

4. Get pdf report for total counts for candidates
URL: http://localhost:8080/pdfreport
Returns a pdf file which has report for count

5.Get total entries in the collection
URL: http://localhost:8080/getall
Returns a list of entries in collection
