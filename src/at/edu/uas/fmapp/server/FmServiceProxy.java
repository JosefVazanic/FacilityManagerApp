package at.edu.uas.fmapp.server;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.xmlrpc.android.XMLRPCClient;

import android.os.AsyncTask;
import at.edu.uas.fmapp.classes.Worker;

@SuppressWarnings("rawtypes")
public class FmServiceProxy {

	private static final String SERVER_URL = "http://wi-gate.technikum-wien.at:60354/xmlrpc";
	private static final String LOCAL_SERVER_URL = "http://192.168.2.4:8080/FacilityManagerService/xmlrpc";
	private static final String SERVICE_NAME = "FacilityManagerService.";

	private static final String METHOD_GET_MASTER_DATA = "getMasterData";
	private static final String METHOD_GET_TASKS = "getTaskList";
	private static final String METHOD_GET_WORKERS = "getWorkerList";
	private static final String METHOD_AUTHENTICATE = "authenticate";
	private static final String METHOD_SUBMIT_TASK_STATUS = "submitTaskStatus";
	private static final String METHOD_SUBMIT_NEW_TASK = "submitNewTask";

	private XMLRPCClient xmlRpcClient;

	public FmServiceProxy() {
		 xmlRpcClient = new XMLRPCClient(SERVER_URL);
//		xmlRpcClient = new XMLRPCClient(LOCAL_SERVER_URL); // for testing
	}

	public void updateMasterData(FmServiceExecutionListener executionListener) {
		executeServiceMethod(METHOD_GET_MASTER_DATA, null, executionListener);
	}

	public void getTaskList(String userId,
			FmServiceExecutionListener executionListener) {
		executeServiceMethod(METHOD_GET_TASKS, new Object[] { userId },
				executionListener);
	}

	public void getWorkers(FmServiceExecutionListener executionListener) {
		executeServiceMethod(METHOD_GET_WORKERS, null, executionListener);
	}

	public void authenticateWorker(Worker worker, String password,
			FmServiceExecutionListener executionListener) {
		executeServiceMethod(METHOD_AUTHENTICATE, new Object[] {
				worker.getId(), worker.getUserName(), password },
				executionListener);
	}

	private void executeServiceMethod(final String methodName,
			final Object[] params,
			final FmServiceExecutionListener executionListener) {
		FmServerAsyncTask task = new FmServerAsyncTask(methodName,
				executionListener);
		task.execute(params);
	}

	private Object processServiceResult(final String methodName,
			final Object serviceResult) {

		Object result = null;

		if (serviceResult == null) {
			return result;
		}

		// TODO
		// service result casten und in DB speichern; wenn notwendig
		// in Model-Klassen umwandeln und als Object-Liste zurückgeben
		// oder nur die Info das es ausgeführt wurde

		if (methodName.equals(METHOD_GET_MASTER_DATA)) {

		} else if (methodName.equals(METHOD_GET_WORKERS)) {
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

		} else if (methodName.equals(METHOD_GET_TASKS)) {
			List<String> taskList = new ArrayList<String>();
			for (Object task : (Object[]) serviceResult) {
				taskList.add((String) task);
			}
			result = taskList;
		} else if (methodName.equals(METHOD_AUTHENTICATE)) {
			result = Boolean.TRUE.equals(serviceResult);
		}
		return result;
	}

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
		protected void onPostExecute(Object result) {
			super.onPostExecute(result);
			Object processedResult = processServiceResult(methodName, result);
			if (executionListener != null) {
				executionListener.onPostExecute(processedResult);
			}
		}
	}

}
