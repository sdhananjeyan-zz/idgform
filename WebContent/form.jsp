<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>IDG - FORM</title>
<script
	src="https://ajax.googleapis.com/ajax/libs/angularjs/1.5.2/angular.min.js"></script>
</head>
<body ng-app="myapp">
	<div ng-controller="formController">
		<h3 align="center">TO-DO Tasks</h3>
		<br> <br>
		<div
			style="margin-left: 10%; margin-right: 10%; width: 80%; height: 300px; overflow: scroll;">
			<table style="width: 100%; align: center;">
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
			style="margin-left: 10%; margin-right: 10%; width: 80%;"
			ng-show="addEnabled">
			<h3 align="center">ADD Task</h3>
			<br>
			<table style="width: 100%">
				<tr>
					<th style="width: 50%">Task</th>
					<td style="width: 50%"><input type="text" style="width: 98%"
						ng-model="taskToAdd" /></td>
				</tr>
				<tr>
					<th style="width: 50%">Start Time</th>
					<td style="width: 50%"><input type="text"
						placeholder="dd-mm-yyyy hh:mm" style="width: 95%; float: right"
						ng-model="startTimeToAdd" /></td>
				</tr>
				<tr>
					<th style="width: 50%">End Time</th>
					<td style="width: 50%"><input type="text"
						placeholder="dd-mm-yyyy hh:mm" style="width: 95%; float: right"
						ng-model="endTimeToAdd" /></td>
				</tr>
				<tr>
					<td style="width: 100%" colspan="2"><button
							ng-click="addNew()">ADD</button></td>
				</tr>
			</table>
		</div>
		<div name="add form"
			style="margin-left: 10%; margin-right: 10%; width: 80%;"
			ng-show="editEnabled">
			<h3 align="center">EDIT Task</h3>
			<br>
			<table style="width: 100%">
				<tr>
					<th style="width: 50%">Task</th>
					<td style="width: 50%"><input type="text" style="width: 98%"
						ng-model="taskToEdit" /></td>
				</tr>
				<tr>
					<th style="width: 50%">Start Time</th>
					<td style="width: 50%"><input type="text"
						placeholder="dd-mm-yyyy hh:mm" style="width: 95%; float: right"
						ng-model="startTimeToEdit" /></td>
				</tr>
				<tr>
					<th style="width: 50%">End Time</th>
					<td style="width: 50%"><input type="text"
						placeholder="dd-mm-yyyy hh:mm" style="width: 95%; float: right"
						ng-model="endTimeToEdit" /></td>
				</tr>
				<tr>
					<td style="width: 50%"><button ng-click="updateTask()">UPDATE</button></td>
					<td style="width: 50%"><button ng-click="cancelEdit()">CANCEL</button></td>
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
					function($scope, $http) {
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

						$scope.initiate();

					});
</script>
<style>
table, th, td {
	border: 1px solid black;
	text-align: center;
}

/* .no-border > table,th,td {
		border: 0px solid black;
		text-align: center;
	} */
</style>
</html>
