/* 
@ITMillApache2LicenseForJavaFiles@
 */

package com.itmill.toolkit.data;

import com.itmill.toolkit.terminal.ErrorMessage;
import com.itmill.toolkit.terminal.PaintException;
import com.itmill.toolkit.terminal.PaintTarget;

/**
 * Object validator interface. Implementors of this class can be added to any
 * {@link com.itmill.toolkit.data.Validatable} object to verify its value. The
 * <code>Validatable#isValid(Object)</code> iterates all registered
 * <code>Validator</code>s, calling their {@link #validate(Object)} methods.
 * <code>validate(Object)</code> should throw the
 * {@link Validator.InvalidValueException} if the given value is not valid by
 * its standards.
 * 
 * @author IT Mill Ltd.
 * @version
 * @VERSION@
 * @since 3.0
 */
public interface Validator {

    /**
     * Checks the given value against this validator. If the value is valid this
     * method should do nothing, and if it's not valid, it should throw
     * <code>Validator.InvalidValueException</code>
     * 
     * @param value
     *                the value to check
     * @throws Validator.InvalidValueException
     *                 if the value is not valid
     */
    public void validate(Object value) throws Validator.InvalidValueException;

    /**
     * Tests if the given value is valid.
     * 
     * @param value
     *                the value to check
     * @return <code>true</code> for valid value, otherwise <code>false</code>.
     */
    public boolean isValid(Object value);

    /**
     * Invalid value exception can be thrown by {@link Validator} when a given
     * value is not valid.
     * 
     * @author IT Mill Ltd.
     * @version
     * @VERSION@
     * @since 3.0
     */
    public class InvalidValueException extends RuntimeException implements
            ErrorMessage {

        /**
         * Serial generated by eclipse.
         */
        private static final long serialVersionUID = 3689073941163422257L;

        /** Array of validation errors that are causing the problem. */
        private InvalidValueException[] causes = null;

        /**
         * Constructs a new <code>InvalidValueException</code> with the
         * specified detail message.
         * 
         * @param message
         *                The detail message of the problem.
         */
        public InvalidValueException(String message) {
            this(message, new InvalidValueException[] {});
        }

        /**
         * Constructs a new <code>InvalidValueException</code> with a set of
         * causing validation exceptions. The error message contains first the
         * given message and then a list of validation errors in the given
         * validatables.
         * 
         * @param message
         *                The detail message of the problem.
         * @param causes
         *                Array of validatables whos invalidities are possiblity
         *                causing the invalidity.
         */
        public InvalidValueException(String message,
                InvalidValueException[] causes) {
            super(message);
            if (causes == null) {
                throw new NullPointerException(
                        "Possible causes array must not be null");
            }
            this.causes = causes;
        }

        public final int getErrorLevel() {
            return ErrorMessage.ERROR;
        }

        public void paint(PaintTarget target) throws PaintException {
            target.startTag("error");
            target.addAttribute("level", "error");

            // Error message
            final String message = getLocalizedMessage();
            if (message != null) {
                target.addText(message);
            }

            // Paint all the causes
            for (int i = 0; i < causes.length; i++) {
                causes[i].paint(target);
            }

            target.endTag("error");
        }

        /* Documented in super interface */
        public void addListener(RepaintRequestListener listener) {
        }

        /* Documented in super interface */
        public void removeListener(RepaintRequestListener listener) {
        }

        /* Documented in super interface */
        public void requestRepaint() {
        }

        /* Documented in super interface */
        public void requestRepaintRequests() {
        }

        public String getDebugId() {
            return null;
        }

        public void setDebugId(String id) {
            throw new UnsupportedOperationException(
                    "Setting testing id for this Paintable is not implemented");
        }

    }

    public class EmptyValueException extends Validator.InvalidValueException {
        /**
         * Serial generated by eclipse.
         */
        private static final long serialVersionUID = -4488988853486652602L;
    
        public EmptyValueException(String message) {
            super(message);
        }
        
    }
}
