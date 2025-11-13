package ir.seydef.plugin.formcounter.model;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

/**
 * @author S.Abolfazl Eftekhari
 */
public class DDMFieldInfo implements Serializable {

    public static class FieldOption implements Serializable {

        public FieldOption() {
        }

        public FieldOption(String label, String value) {
            _label = label;
            _value = value;
        }

        public String getLabel() {
            return _label;
        }

        public String getValue() {
            return _value;
        }

        public void setLabel(String label) {
            _label = label;
        }

        public void setValue(String value) {
            _value = value;
        }

        private static final long serialVersionUID = 1L;

        private String _label;
        private String _value;

    }

    public DDMFieldInfo() {
        _options = new ArrayList<>();
    }

    public DDMFieldInfo(String name, String label, String type) {
        _name = name;
        _label = label;
        _type = type;
        _options = new ArrayList<>();
    }

    public void addOption(FieldOption option) {
        _options.add(option);
    }

    public void addOption(String label, String value) {
        _options.add(new FieldOption(label, value));
    }

    public String getLabel() {
        return _label;
    }

    public String getName() {
        return _name;
    }

    public List<FieldOption> getOptions() {
        return _options;
    }

    public String getType() {
        return _type;
    }

    public void setLabel(String label) {
        _label = label;
    }

    public void setName(String name) {
        _name = name;
    }

    public void setOptions(List<FieldOption> options) {
        _options = options;
    }

    public void setType(String type) {
        _type = type;
    }

    private static final long serialVersionUID = 1L;

    private String _label;
    private String _name;
    private List<FieldOption> _options;
    private String _type;

}
