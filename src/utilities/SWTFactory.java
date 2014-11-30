package utilities;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;


public class SWTFactory {
	
	public static void createNumOnly(Text t, String message, int width, int height) {
		t.setMessage(message);
		t.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
				false, width, height));
		SWTFactory.verifyText(t, true);
	}
	
	public static void createText(Text t, String message, int width, int height) {
		t.setMessage(message);
		t.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
				false, width, height));
		SWTFactory.verifyText(t, false);
	}
	
	public static void createNumLimited(Text t, int limit, String message, int width, int height) {
		t.setTextLimit(limit);
		t.setMessage(message);
		t.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
				false, width, height));
		SWTFactory.verifyText(t, true);
	}
	
	public static void createLimitedFill(Text t, int limit, String message, int width, int height) {
		t.setTextLimit(limit);
		t.setMessage(message);
		t.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true,
				true, width, height));
		SWTFactory.verifyText(t, false);
	}
	
	public static void errorMessage(Shell shell, String message, String text) {
		MessageBox mb = new MessageBox(shell, SWT.ICON_ERROR | SWT.OK);
		mb.setMessage(message);
		mb.setText(text);
		mb.open();
	}
	
	public static boolean errorOptionMessage(Shell shell, String message, String text) {
		MessageBox mb = new MessageBox(shell, SWT.ICON_ERROR | SWT.YES | SWT.NO);
		mb.setMessage(message);
		mb.setText(text);
		if (mb.open() == SWT.NO)
			return false;
		return true;
	}
	
	public static boolean warningOptionMessage(Shell shell, String message, String text) {
		MessageBox mb = new MessageBox(shell, SWT.ICON_WARNING | SWT.YES | SWT.NO);
		mb.setMessage(message);
		mb.setText(text);
		if (mb.open() == SWT.NO)
			return false;
		return true;
	}
	
	public static void notificationMessage(Shell shell, String message, String text) {
		MessageBox mb = new MessageBox(shell, SWT.OK);
		mb.setMessage(message);
		mb.setText(text);
		mb.open();
	}
	
	public static void verifyText(final Text t, final boolean numberOnly) {
		t.addVerifyListener(new VerifyListener() {
			public void verifyText(VerifyEvent e) {
				if (!t.isFocusControl())
					return;
				char c = e.character;
				if (e.keyCode == SWT.DEL || e.keyCode == SWT.BS) {
					return;
				}
				if (numberOnly) {
					if (c < '0' || c > '9') {
						e.doit = false;
						return;
					}
				}
				e.doit = c != ',';
			}
		});
	}

}
