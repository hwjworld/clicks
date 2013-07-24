package requestReceiver;

import operation.DBSaver;
import operation.DBViewer;
import operation.ISaver;
import operation.IViewer;
import config.Config;

public class OperationFactory {
	public static ISaver getSaver(Config config) {
		ISaver saver = null;
		if(config.getStoretype()==1)
			saver = new DBSaver();
		return saver;
	}
	public static IViewer getViewer(Config config) {
		IViewer viewer = null;
		if(config.getStoretype()==1)
			viewer = new DBViewer();
		return viewer;
	}
}
