import 'package:flutter/material.dart';
import 'package:kartal/kartal.dart';

class CustomTextField extends StatefulWidget {
  final Widget? child;
  final double? height;
  final double? width;
  final Widget? suffixIcon;
  final Widget? prefixIcon;
  final String? hinttext;
  final bool obscureText;
  final ValueChanged<String>? onChanged;

  const CustomTextField({
    Key? key,
    this.height,
    this.width,
    this.hinttext,
    this.suffixIcon,
    this.prefixIcon,
    this.child,
    this.obscureText = false,
    this.onChanged,
  }) : super(key: key);

  @override
  _CustomTextFieldState createState() => _CustomTextFieldState();
}
class _CustomTextFieldState extends State<CustomTextField> {

  @override
  void initState() {
    // TODO: implement initState
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    return Container(
      width: widget.width,
      height: widget.height,
      decoration: BoxDecoration(
        boxShadow: const [
          BoxShadow(
            color: Colors.white,
            offset: Offset(0.0, 1.0),
            blurRadius: 6.0,
          ),
        ],
        border: Border.all(color: Colors.black12),
        borderRadius: const BorderRadius.all(Radius.circular(15.0)),
      ),
        child: Center(
          child: TextFormField(
            obscureText: widget.obscureText,
            onChanged: widget.onChanged,
            decoration: InputDecoration(
              prefixIcon: widget.prefixIcon,
              suffixIcon: widget.suffixIcon,
              hintText: widget.hinttext,
              hintStyle: context.textTheme.bodyText1!
                  .copyWith(fontWeight: FontWeight.normal),
              border: OutlineInputBorder(
                borderSide: BorderSide.none,
                borderRadius: BorderRadius.circular(10.0),
              ),
            ),
          ),
        ),
    );
  }

  @override
  void dispose() {
    super.dispose();
  }
}
