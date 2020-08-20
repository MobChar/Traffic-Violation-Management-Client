package client_services;


import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public interface CallBackHandler extends Remote{
	public void callBack(Object payload) throws RemoteException;
	public void errorCallBack(String message) throws RemoteException;
}


