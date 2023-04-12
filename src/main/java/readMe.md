## APIs

---

### Account APIs

---

#### create new account:

`/account/signup`

request : 
`{type, name, department, designation, email, pin}`

response:
`{statusCode, message, data:{ adminId, empId, email }}`
---

#### login to account:
`/account/login`


request :
`{type, email, pin}`

response:
`{statusCode, message, data:{ adminId*, empId, email }}`

---


#### get all account details:
`/account/allDetail`


request :
`{}`

response:
`{statusCode, message, data:{ email, adminId, empId, name, department, designation }}`

---

### Attendance APIs

---

#### check-in
`/account/checkIn`

request:
`{eid, date}`

response:
`{data:{date, checkIn}, statusCode, message}`

---

#### check-out
`/account/checkOut`

request:
`{eid, date}`

response:
`{data:{date, checkIn, checkOut, totalHours}, statusCode, message}`

---

#### check-attendance
`/account/checkAttendance`

request:
`{eid, date}`

response:
`{statusCode, message, data:{found, date, checkIn, checkOut, totalHours}}`

---

#### show-attendances
`/account/showAttendances`

request:
`{eid}`

response:
`{statusCode, message, data:[attendance(date, checkIn, checkOut, totalHours)]}`

---

### Leave APIs

---

#### apply-leave
`/leave/applyLeave`

request:
`{empId, fromDate, toDate, reason}`

response:
`{statusCode, message, data:{lid, eid, to, from, reason, status}}`

---

#### get-leave-status
`/leave/getLeaveStatus`

request:
`{eid, lid}`

response:
`{statusCode, message, data:{fromDate, toDate, reason, status}}`

--- 

#### update-leave
`/leave/updateLeave`

request:
`{eid, lid}`

response:
`{statusCode, message, data:{fromDate, toDate, reason, status}}`

--- 

#### show-leaves
`/leave/showLeaves`

request:
`{eid}`

response:
`{statusCode, message, data:[leave(lid, fromDate, toDate, reason, leaveStatus)]}`

---