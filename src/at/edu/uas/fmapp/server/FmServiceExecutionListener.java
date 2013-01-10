package at.edu.uas.fmapp.server;

public interface FmServiceExecutionListener<T> {

	void onPostExecute(T result);

}
