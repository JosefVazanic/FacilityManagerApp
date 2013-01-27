package at.edu.uas.fmapp.server;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.xmlrpc.android.XMLRPCClient;

import android.os.AsyncTask;
import at.edu.uas.fmapp.entity.AdditionalWorkItem;
import at.edu.uas.fmapp.entity.Address;
import at.edu.uas.fmapp.entity.Person;
import at.edu.uas.fmapp.entity.Task;
import at.edu.uas.fmapp.entity.TaskAssignment;
import at.edu.uas.fmapp.entity.WorkItem;
import at.edu.uas.fmapp.entity.WorkObject;
import at.edu.uas.fmapp.entity.Worker;

public class FmServiceProxy {

	private static final String SERVER_URL = "http://wi-gate.technikum-wien.at:60354/xmlrpc";
	private static final String LOCAL_SERVER_URL = "http://192.168.2.4:8080/FacilityManagerService/xmlrpc";
	private static final String SERVICE_NAME = "FacilityManagerService.";

	private static final String METHOD_GET_WORK_OBJECT_LIST = "getWorkObjectList";
	private static final String METHOD_GET_TASK_LIST = "getTaskList";
	private static final String METHOD_GET_TASK_ASSIGNMENT_LIST = "getTaskAssignmentList";
	private static final String METHOD_GET_WORKER_LIST = "getWorkerList";
	private static final String METHOD_AUTHENTICATE = "authenticate";
	private static final String METHOD_INSERT_WORK_ITEM = "insertWorkItem";
	private static final String METHOD_INSERT_ADDITIONAL_WORK_ITEM = "insertAdditionalWorkItem";

	private XMLRPCClient xmlRpcClient;

	public FmServiceProxy() {
		boolean localTest = false; // set true to test with local server
		xmlRpcClient = new XMLRPCClient(localTest ? LOCAL_SERVER_URL
				: SERVER_URL);
	}

	public void getWorkObjectList(Long userId,
			FmServiceExecutionListener<List<WorkObject>> executionListener) {
		executeServiceMethod(METHOD_GET_WORK_OBJECT_LIST, new Object[] { userId },
				executionListener);
	}

	public void getTaskList(
			FmServiceExecutionListener<List<Task>> executionListener) {
		executeServiceMethod(METHOD_GET_TASK_LIST, null, executionListener);
	}

	public void getTaskAssignmentList(Long userId,
			FmServiceExecutionListener<List<TaskAssignment>> executionListener) {
		executeServiceMethod(METHOD_GET_TASK_ASSIGNMENT_LIST,
				new Object[] { userId }, executionListener);
	}

	public void getWorkerList(
			FmServiceExecutionListener<List<Worker>> executionListener) {
		executeServiceMethod(METHOD_GET_WORKER_LIST, null, executionListener);
	}

	public void authenticateWorker(String username, String password,
			FmServiceExecutionListener<Boolean> executionListener) {
		executeServiceMethod(METHOD_AUTHENTICATE, new Object[] { username,
				password }, executionListener);
	}

	public void insertWorkItem(WorkItem workItem,
			FmServiceExecutionListener<Boolean> executionListener) {
		executeServiceMethod(
				METHOD_INSERT_WORK_ITEM,
				new Object[] { workItem.getTaskAssignmentId(),
						workItem.getStatus(), workItem.getDate() },
				executionListener);
	}

	public void insertAdditionalWorkItem(AdditionalWorkItem additionalWorkItem,
			FmServiceExecutionListener<Boolean> executionListener) {
		executeServiceMethod(
				METHOD_INSERT_ADDITIONAL_WORK_ITEM,
				new Object[] { additionalWorkItem.getTaskName(),
						additionalWorkItem.getTaskDescription(),
						additionalWorkItem.getFrequency(),
						additionalWorkItem.getFrequencyInformation(),
						additionalWorkItem.getStatus(),
						additionalWorkItem.getWorkerId(),
						additionalWorkItem.getWorkObjectId() },
				executionListener);
	}

	private Object processServiceResult(final String methodName,
			final Object serviceResult) {

		Object result = null;

		if (serviceResult == null) {
			return result;
		}

		if (methodName.equals(METHOD_GET_WORK_OBJECT_LIST)) {

			List<WorkObject> workerObjectList = new ArrayList<WorkObject>();

			for (Object workerObjectDataObject : (Object[]) serviceResult) {

				Object[] workerObjectData = (Object[]) workerObjectDataObject;
				WorkObject workObject = new WorkObject(
						(Long) workerObjectData[0]);

				workObject.setNumber((String) workerObjectData[1]);
				workObject.setDescription((String) workerObjectData[2]);

				Object[] addressData = (Object[]) workerObjectData[3];
				Address address = new Address((Long) addressData[0]);
				workObject.setAddress(address);

				address.setCountry((String) addressData[1]);
				address.setCity((String) addressData[2]);
				address.setZipCode((String) addressData[3]);
				address.setStreet((String) addressData[4]);
				address.setNumber((String) addressData[5]);
				address.setLatitude((Long) addressData[6]);
				address.setLongitude((Long) addressData[7]);

				Object[] contactPersonData = (Object[]) workerObjectData[4];
				Person contactPerson = new Person((Long) contactPersonData[0]);
				workObject.setContactPerson(contactPerson);

				contactPerson.setFirstName((String) contactPersonData[1]);
				contactPerson.setLastName((String) contactPersonData[2]);
				contactPerson.setDateOfBirth((Date) contactPersonData[3]);
				contactPerson.setMobile((String) contactPersonData[4]);
				contactPerson.setPhone((String) contactPersonData[5]);
				contactPerson.setEmail((String) contactPersonData[6]);

				workerObjectList.add(workObject);
			}

			result = workerObjectList;

		} else if (methodName.equals(METHOD_GET_TASK_LIST)) {

			List<Task> taskList = new ArrayList<Task>();

			for (Object taskDataObject : (Object[]) serviceResult) {

				Object[] taskData = (Object[]) taskDataObject;

				Task task = new Task((Long) taskData[0]);
				task.setName((String) taskData[1]);
				task.setDescription((String) taskData[2]);

				taskList.add(task);
			}

			result = taskList;

		} else if (methodName.equals(METHOD_GET_TASK_ASSIGNMENT_LIST)) {

			List<TaskAssignment> taskAssignmentList = new ArrayList<TaskAssignment>();

			for (Object taskAssignmentDataObject : (Object[]) serviceResult) {

				Object[] taskAssignmentData = (Object[]) taskAssignmentDataObject;

				TaskAssignment taskAssignment = new TaskAssignment(
						(Long) taskAssignmentData[0]);
				taskAssignment.setFrequency((String) taskAssignmentData[1]);
				taskAssignment
						.setFrequencyInformation((String) taskAssignmentData[2]);
				taskAssignment.setTaskId((Long) taskAssignmentData[3]);
				taskAssignment.setWorkObjectId((Long) taskAssignmentData[4]);
				taskAssignment.setWorkerId((Long) taskAssignmentData[5]);

				taskAssignmentList.add(taskAssignment);
			}

			result = taskAssignmentList;

		} else if (methodName.equals(METHOD_GET_WORKER_LIST)) {

			List<Worker> workerList = new ArrayList<Worker>();

			for (Object workerDataObject : (Object[]) serviceResult) {

				Object[] workerData = (Object[]) workerDataObject;

				Worker worker = new Worker((Long) workerData[0]);
				worker.setUserName((String) workerData[1]);
				worker.setFirstName((String) workerData[2]);
				worker.setLastName((String) workerData[3]);
				worker.setDateOfBirth((Date) workerData[4]);
				worker.setMobile((String) workerData[5]);
				worker.setPhone((String) workerData[6]);
				worker.setEmail((String) workerData[7]);
				worker.setLatitude((Long) workerData[8]);
				worker.setLongitude((Long) workerData[9]);

				workerList.add(worker);
			}

			result = workerList;

		} else if (methodName.equals(METHOD_AUTHENTICATE)
				|| methodName.equals(METHOD_INSERT_WORK_ITEM)
				|| methodName.equals(METHOD_INSERT_ADDITIONAL_WORK_ITEM)) {

			result = Boolean.TRUE.equals(serviceResult);

		}
		return result;
	}

	@SuppressWarnings("rawtypes")
	private void executeServiceMethod(final String methodName,
			final Object[] params,
			final FmServiceExecutionListener executionListener) {
		FmServerAsyncTask task = new FmServerAsyncTask(methodName,
				executionListener);
		task.execute(params);
	}

	@SuppressWarnings("rawtypes")
	private class FmServerAsyncTask extends AsyncTask<Object, Void, Object> {

		private FmServiceExecutionListener executionListener;
		private String methodName;

		public FmServerAsyncTask(String methodName,
				FmServiceExecutionListener executionListener) {
			this.executionListener = executionListener;
			this.methodName = methodName;
		}

		@Override
		protected Object doInBackground(Object... params) {
			Object result = null;
			try {
				String serviceMethod = SERVICE_NAME + methodName;
				if (params != null) {
					result = xmlRpcClient.call(serviceMethod, params);
				} else {
					result = xmlRpcClient.call(serviceMethod);
				}
			} catch (Exception e) {
				System.out.println("Failed: " + e.getMessage());
			}
			return result;
		}

		@Override
		@SuppressWarnings("unchecked")
		protected void onPostExecute(Object result) {
			super.onPostExecute(result);
			Object processedResult = processServiceResult(methodName, result);
			if (executionListener != null) {
				executionListener.onPostExecute(processedResult);
			}
		}
	}

}
