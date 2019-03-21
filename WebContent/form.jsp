<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>IDG - FORM</title>
<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.5.2/angular.min.js"></script>
<script type="text/javascript" src="jquery/jquery-1.8.3.min.js" charset="UTF-8"></script>
<script type="text/javascript" src="bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript" src="js/bootstrap-datetimepicker.js" charset="UTF-8"></script>
<script type="text/javascript" src="js/locales/bootstrap-datetimepicker.fr.js" charset="UTF-8"></script>
 <link href="bootstrap/css/bootstrap.min.css" rel="stylesheet" media="screen">
 <link href="css/bootstrap-datetimepicker.min.css" rel="stylesheet" media="screen">
</head>
<body ng-app="myapp">
	<div align="right">
		<s:property value="logedInUser"/>&nbsp;&nbsp;&nbsp;&nbsp;
		<a href="/idgform/logout.action">Log out</a>
	</div>
	<div ng-controller="formController">
		<h3 align="center">TO-DO Tasks</h3>
		<br> <br>
		<div
			style="margin-left: 10%; margin-right: 10%; width: 80%; height: 300px; overflow: scroll;">
			<table class="list" style="width: 100%; align: center;">
				<tr>
					<th width="10%">Task Id</th>
					<th width="40%">Description</th>
					<th width="20%">Start Time</th>
					<th width="20%">End Time</th>
					<th width="10%">Operation</th>
				</tr>
				<tr ng-repeat="task in tasks">
					<td>{{task.id}}</td>
					<td>{{task.taskDescription}}</td>
					<td>{{task.startTime}}</td>
					<td>{{task.endTime}}</td>
					<td><button ng-click="edit(task)">EDIT</button>
						<button ng-click="deleteTask(task)">DELETE</button></td>
				</tr>
			</table>
		</div>
		<br>
		<div name="add form"
			style="margin-left: 10%; margin-right: 10%; width: 80%;background-color: #f2f2f2;"
			ng-show="addEnabled">
			<h3 align="center">ADD Task</h3>
			<br>
			<table class="form" style="width: 60%">
				<tr>
					<th style="width: 50%">Task</th>
					<td style="width: 50%">
					<input type="text" style="width: 98%" ng-model="taskToAdd"/></td>
				</tr>
				<tr>
					<th style="width: 50%">Start Time</th>
					<td style="width: 50%"><input type="text" class="form_datetime"
						placeholder="dd-mm-yyyy hh:mm" style="width: 50%;"
						ng-model="startTimeToAdd" id="addStartDateId" /></td>
				</tr>
				<tr>
					<th style="width: 50%">End Time</th>
					<td style="width: 50%"><input type="text" class="form_datetime"
						placeholder="dd-mm-yyyy hh:mm" style="width: 50%;"
						ng-model="endTimeToAdd" id="addEndDateId" /></td>
				</tr>
				<tr>
					<td style="width: 100%" colspan="2"><button style="width:30%;" ng-click="addNew()">ADD</button></td>
				</tr>
			</table>
		</div>
		<div name="add form"
			style="margin-left: 10%; margin-right: 10%; width: 80%;background-color: #f2f2f2;"
			ng-show="editEnabled">
			<h3 align="center">EDIT Task</h3>
			<br>
			<table class="form" style="width: 60%">
				<tr>
					<th style="width: 50%">Task</th>
					<td style="width: 50%"><input type="text" style="width: 98%"
						ng-model="taskToEdit" /></td>
				</tr>
				<tr>
					<th style="width: 50%">Start Time</th>
					<td style="width: 50%"><input type="text"
						placeholder="dd-mm-yyyy hh:mm" style="width: 50%;" class="form_datetime"
						ng-model="startTimeToEdit" id="editStartDateId" /></td>
				</tr>
				<tr>
					<th style="width: 50%">End Time</th>
					<td style="width: 50%"><input type="text"
						placeholder="dd-mm-yyyy hh:mm" style="width: 50%;" class="form_datetime"
						ng-model="endTimeToEdit" id="editEndDateId"/></td>
				</tr>
				<tr>
					<td style="width: 50%"><button style="width:30%;" ng-click="updateTask()">UPDATE</button></td>
					<td style="width: 50%"><button style="width:30%;" ng-click="cancelEdit()">CANCEL</button></td>
				</tr> 
			</table>
		</div>
	</div>
</body>
<script>
	angular
			.module("myapp", [])
			.controller(
					"formController",
					function($scope, $http,$interval) {
						$scope.initiate = function() {
							$http
									.get("/idgform/list_tasks.action")
									.then(
											function(response) {
												//{"response":{"data":[],"status":"success"}}
												$scope.tasks = response.data['response']['data'];

											});

						}
						$scope.tasks = [

						];
						$scope.addEnabled = true;
						$scope.editEnabled = false;
						$scope.edit = function(task) {
							$scope.addEnabled = false;
							$scope.editEnabled = true;
							$scope.taskToEdit = task.taskDescription;
							$scope.startTimeToEdit = task.startTime;
							$scope.endTimeToEdit = task.endTime;
							$scope.IdToEdit = task.id;

						}

						$scope.cancelEdit = function() {
							$scope.addEnabled = true;
							$scope.editEnabled = false;
						}


						$scope.taskToAdd;
						$scope.startTimeToAdd;
						$scope.endTimeToAdd;

						$scope.taskToEdit;
						$scope.startTimeToEdit;
						$scope.endTimeToEdit;
						$scope.IdToEdit;

						$scope.addNew = function() {
							
							var data = {
								description : $scope.taskToAdd,
								startTime : $scope.startTimeToAdd,
								endTime : $scope.endTimeToAdd
							}
							$http
									.post('/idgform/add_task.action', data)
									.success(
											function(data, status, headers,
													config) {
												alert(data['response']['status']
														+ "   "
														+ data['response']['message'])
												$scope.taskToAdd = "";
												$scope.startTimeToAdd = "";
												$scope.endTimeToAdd = "";
												$scope.initiate();
											})
						}

						$scope.updateTask = function() {

							var data = {
								id : $scope.IdToEdit,
								description : $scope.taskToEdit,
								startTime : $scope.startTimeToEdit,
								endTime : $scope.endTimeToEdit
							}

							$http
									.post('/idgform/update_task.action', data)
									.success(
											function(data, status, headers,
													config) {
												alert(data['response']['status']
														+ "   "
														+ data['response']['message'])
												$scope.cancelEdit();
												$scope.initiate();
											})
						}
						
						$scope.deleteTask = function(task) {

							var data = {
								id : task.id,
							}

							$http
									.post('/idgform/delete_task.action', data)
									.success(
											function(data, status, headers,
													config) {
												alert(data['response']['status']
														+ "   "
														+ data['response']['message'])
												$scope.initiate();
											})
						}

						$(".form_datetime").datetimepicker({
				    		format: 'dd-mm-yyyy hh:ii'
				    	});
						$("#addStartDateId").change(function(){
							$scope.startTimeToAdd = $("#addStartDateId").val();
						})
						$("#addEndDateId").change(function(){
							$scope.endTimeToAdd = $("#addEndDateId").val();
						})
						$("#editStartDateId").change(function(){
							$scope.startTimeToEdit = $("#editStartDateId").val();
						})
						$("#editEndDateId").change(function(){
							$scope.endTimeToEdit = $("#editEndDateId").val();
						})
						$scope.initiate();
						$interval(function(){
							$scope.initiate();
						},10000)
						
					});
    	
</script>
<style>

.list td {
  padding: 15px;
  text-align: center;
  border-bottom: 1px solid #ddd;
}

.list th {
  padding: 15px;
  text-align: center;
  border-bottom: 1px solid #ddd;
  background: #bfd7fc;
}

.list tr:nth-child(even) {
	background-color: #f2f2f2;
}


.form td,th {
  padding: 15px;
  text-align: center;
  align-content: center;
  
}
.form{
	margin-left: 20%;
	margin-right: 20%;
}


/* .no-border > table,th,td {
		border: 0px solid black;
		text-align: center;
	} */
</style>
</html>
