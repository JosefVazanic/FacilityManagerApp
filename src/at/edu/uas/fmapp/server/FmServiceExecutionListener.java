package at.edu.uas.fmapp.server;


public interface FmServiceExecutionListener<T> {

	public  void onPostExecute(T result);

}
